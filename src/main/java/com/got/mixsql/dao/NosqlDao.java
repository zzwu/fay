package com.got.mixsql.dao;

public interface NosqlDao<K, V> {
	
	boolean insert(K k, V v);
	
	boolean update(K k, V v);
	
	boolean delete(K k);
	
	V get(K k);
	
}
