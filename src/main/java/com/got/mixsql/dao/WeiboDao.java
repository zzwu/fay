package com.got.mixsql.dao;

import com.got.mixsql.annotation.NoSqlDao;
import com.got.mixsql.annotation.NoSqlEntityClass;
import com.got.mixsql.model.Weibo;

@NoSqlDao
@NoSqlEntityClass(Weibo.class)
public interface WeiboDao {

}
