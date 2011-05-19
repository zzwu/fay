package com.got.mixsql.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import voldemort.client.RoutingTier;
import voldemort.serialization.SerializerDefinition;
import voldemort.store.StoreDefinition;
import voldemort.store.StoreDefinitionBuilder;

import com.got.mixsql.annotation.NoSqlAutoIncKey;
import com.got.mixsql.annotation.NosqlKey;

/**
 * 用于EntityMetaInfo的汇总管理。
 * @author zzwu
 */
@SuppressWarnings("rawtypes")
public class EntityManager {
	
	private Map<Class, EntityMetaInfo> metaInfoMap = new HashMap<Class, EntityMetaInfo>();
	
	private static EntityManager  instance = new EntityManager();
	
	private EntityManager() {
		//do nothing
	}

	public static EntityManager getInstance() {
		return instance;
	}
	
	public EntityMetaInfo getMetaInfo(Class c) {
		EntityMetaInfo info = metaInfoMap.get(c);
		if (null == info) {
			info = new EntityMetaInfo();
			info.setC(c);
			info.setFields(c.getDeclaredFields());
			for (Field f : info.getFields()) {
				if (null != f.getAnnotation(NoSqlAutoIncKey.class)) {
					info.setAutoIncField(f);
					SerializerDefinition keySerializer = buildSerializerDefinition("java-serialization", "[LB");
					SerializerDefinition valueSerializer = buildSerializerDefinition("java-serialization", Long.class.getCanonicalName());
					String keyStoreName = c.getCanonicalName() + "_id";
					info.setKeyStoreName(keyStoreName);
					StoreDefinition autoIncKeyDefinifion = new StoreDefinitionBuilder()
							.setName(keyStoreName).setType("bdb")
							.setKeySerializer(keySerializer)
							.setValueSerializer(valueSerializer)
							.setRoutingPolicy(RoutingTier.CLIENT)
							.setRoutingStrategyType("consistent-routing")
							.setReplicationFactor(1).setPreferredReads(1)
							.setRequiredReads(1).setPreferredWrites(1)
							.setRequiredWrites(1).setRetentionPeriodDays(null)
							.setRetentionScanThrottleRate(null).build();
					info.setAutoIncKeyDefinifion(autoIncKeyDefinifion);
				}
				if (null != f.getAnnotation(NosqlKey.class)) {
					info.setKeyField(f);
				}
			}
			//生成voldemort存储信息
			SerializerDefinition keySerializer = buildSerializerDefinition(
					"java-serialization", "[LB");
			SerializerDefinition valueSerializer = buildSerializerDefinition(
					"java-serialization", c.getCanonicalName());
			StoreDefinition sd = new StoreDefinitionBuilder().setName(c.getCanonicalName())
					.setType("bdb").setKeySerializer(keySerializer)
					.setValueSerializer(valueSerializer)
					.setRoutingPolicy(RoutingTier.CLIENT)
					.setRoutingStrategyType("consistent-routing")
					.setReplicationFactor(1).setPreferredReads(1)
					.setRequiredReads(1).setPreferredWrites(1)
					.setRequiredWrites(1).setRetentionPeriodDays(null)
					.setRetentionScanThrottleRate(null).build();
			info.setStoreDef(sd);
			metaInfoMap.put(c, info);
		}
		return metaInfoMap.get(c);
	}
	
	private SerializerDefinition buildSerializerDefinition(String name, String schema) {
        Map<Integer, String> schemaInfosByVersion = new HashMap<Integer, String>();
        schemaInfosByVersion.put(0, schema);
        return new SerializerDefinition(name, schemaInfosByVersion, true, null);
    }
	
	public void putMetaInfo(Class c, EntityMetaInfo m) {
		metaInfoMap.put(c, m);
	}
	
	/**
	 * 获取EntityManager对应的voldemort的StoreDefinition List。
	 * @return
	 */
	public List<StoreDefinition> getStores() {
		List<StoreDefinition> stores = new ArrayList<StoreDefinition>();
		for (Class c : metaInfoMap.keySet()) {
			stores.add(metaInfoMap.get(c).getStoreDef());
		}
		return stores;
	}
}
