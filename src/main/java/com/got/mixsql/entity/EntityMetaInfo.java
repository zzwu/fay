package com.got.mixsql.entity;

import java.lang.reflect.Field;

import voldemort.store.StoreDefinition;

@SuppressWarnings("rawtypes")
public class EntityMetaInfo {
	
	private Class c;
	private Field[] fields;
	private Field autoIncField;
	private Field keyField;
	private StoreDefinition storeDef;
	private StoreDefinition autoIncKeyDefinifion;
	private String keyStoreName;

	public Class getC() {
		return c;
	}

	public void setC(Class c) {
		this.c = c;
	}

	public Field[] getFields() {
		return fields;
	}

	public void setFields(Field[] fields) {
		this.fields = fields;
	}

	public Field getAutoIncField() {
		return autoIncField;
	}

	public void setAutoIncField(Field autoIncField) {
		this.autoIncField = autoIncField;
	}

	public Field getKeyField() {
		return keyField;
	}

	public void setKeyField(Field keyField) {
		this.keyField = keyField;
	}

	public StoreDefinition getStoreDef() {
		return storeDef;
	}

	public void setStoreDef(StoreDefinition storeDef) {
		this.storeDef = storeDef;
	}

	public StoreDefinition getAutoIncKeyDefinifion() {
		return autoIncKeyDefinifion;
	}

	public void setAutoIncKeyDefinifion(StoreDefinition autoIncKeyDefinifion) {
		this.autoIncKeyDefinifion = autoIncKeyDefinifion;
	}

	public String getKeyStoreName() {
		return keyStoreName;
	}

	public void setKeyStoreName(String keyStoreName) {
		this.keyStoreName = keyStoreName;
	}

	
	
}
