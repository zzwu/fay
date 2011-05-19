package com.got.mixsql.model;

import java.io.Serializable;
import java.util.Date;

import com.got.mixsql.annotation.NoSqlAutoIncKey;
import com.got.mixsql.annotation.NosqlKey;

public class User implements Serializable {
	
	private static final long serialVersionUID = 4149407409011096125L;

	@NoSqlAutoIncKey
	private int id;
	
	@NosqlKey
	private String userName;
	
	private String email;
	
	//md5
	private String psw;

	private Date birthday;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	
}
