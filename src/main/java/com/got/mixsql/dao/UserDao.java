package com.got.mixsql.dao;

import com.got.mixsql.annotation.NoSqlDao;
import com.got.mixsql.annotation.NoSqlEntityClass;
import com.got.mixsql.model.User;

/**
 * 用户dao。
 * @author zzwu
 *
 */
@NoSqlDao
@NoSqlEntityClass(User.class)
public interface UserDao {
	
	User getUserByName(String name);
	
	boolean addUser(String userName, User user);
	
	boolean update(String userName, User user);

}
