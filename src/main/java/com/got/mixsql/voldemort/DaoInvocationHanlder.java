package com.got.mixsql.voldemort;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;

import com.got.mixsql.annotation.NoSqlEntityClass;
import com.got.mixsql.entity.EntityManager;
import com.got.mixsql.entity.EntityMetaInfo;

@SuppressWarnings("rawtypes")

public class DaoInvocationHanlder implements InvocationHandler {
	
	private Class c;
	private SocketStoreClientFactory factory;
	private StoreClient sc;
	private StoreClient autoKeySC;
	
	public DaoInvocationHanlder(Class c) {
		this.c = c;
		initClientConfig();
	}

	@SuppressWarnings("unchecked")
	private void initClientConfig() {
		ClientConfig config = new ClientConfig();
        config.setBootstrapUrls("tcp://localhost:6666");
        config.setMaxThreads(10);
        factory = new SocketStoreClientFactory(config);
        EntityManager manager = EntityManager.getInstance();
        Class entityClass = ((NoSqlEntityClass)c.getAnnotation(NoSqlEntityClass.class)).value();
		EntityMetaInfo info = manager.getMetaInfo(entityClass);
		sc = factory.getStoreClient(info.getStoreDef().getName());
		if (null != info.getAutoIncKeyDefinifion()) {
			autoKeySC = factory.getStoreClient(info.getKeyStoreName());
		}
	}

	@SuppressWarnings("unchecked")
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String methodName = method.getName();
		if (methodName.indexOf("add") > -1 || methodName.indexOf("insert") > -1 || methodName.indexOf("create") > -1) {
			//do put
			if (null != autoKeySC) {
				long id = (Long) autoKeySC.get("id").getValue();
				if (args[0] instanceof Collection) {
					Collection objs = (Collection)args[0];
					for (Object o : objs) {
						sc.put(id++, o);
					}
				} else {
					sc.put(id++, args[0]);
				}
				autoKeySC.put("id", id);
			} else {
				sc.put(args[0], args[1]);
			}
			return true;
		}
		if (methodName.indexOf("delete") > -1 || methodName.indexOf("remove") > -1) {
			sc.delete(args[0]);
			return true;
		}
		if (methodName.indexOf("update") > -1) {
			//do update
			sc.put(args[0], args[1]);
			return true;
		}
		if (methodName.indexOf("get") > -1 || methodName.indexOf("select") > -1 || methodName.indexOf("query") > -1) {
			//do get
			return sc.get(args[0]).getValue();
		}
		return null;
	}

}
