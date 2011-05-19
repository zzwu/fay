package com.got.mixsql.dao;

import java.lang.reflect.Proxy;

import com.got.mixsql.voldemort.DaoInvocationHanlder;

public class DaoProxy {
	
	@SuppressWarnings("unchecked")
	public static <T> T create(Class<T> c) {
		
		return (T)Proxy.newProxyInstance(c.getClassLoader(), new Class[] {c}, new DaoInvocationHanlder(c));

	}

}
