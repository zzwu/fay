package com.got.mixsql.entity;

import org.junit.Test;

import com.got.mixsql.model.User;

public class EntityManagerTest {

	@Test
	public void testGetInstance() {
		//EntityManager m = EntityManager.getInstance();
	}

	@Test
	public void testGetMetaInfo() {
		EntityManager m = EntityManager.getInstance();
		m.getMetaInfo(User.class);
	}

	@Test
	public void testPutMetaInfo() {
		
	}

}
