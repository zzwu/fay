package com.got.mixsql.dao.test;

import java.util.Date;

import com.got.mixsql.dao.DaoProxy;
import com.got.mixsql.dao.UserDao;
import com.got.mixsql.entity.EntityManager;
import com.got.mixsql.model.User;
import com.got.mixsql.voldemort.VoldemortServiceControler;

public class TestSome {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		EntityManager manager = EntityManager.getInstance();
		manager.getMetaInfo(User.class);
		VoldemortServiceControler c = new VoldemortServiceControler(manager.getStores());
		c.run();
		
		User u = new User();
		u.setEmail("zhenzhouwu@yahoo.cn");
		u.setUserName("zzwu");
		
		UserDao ud = DaoProxy.create(UserDao.class);
		ud.addUser(u.getUserName(), u);
		
		User uu = ud.getUserByName("zzwu");
		System.out.println(uu.getEmail());
		
		uu.setBirthday(new Date());
		
		ud.update(uu.getUserName(), uu);
		
		uu = ud.getUserByName("zzwu");
		System.out.println(uu.getBirthday());
		
		c.stop();

	}

}
