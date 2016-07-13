package com.zh.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zh.entity.User;
@Repository
public class UserDao extends BaseDao<User>{
	
	public List<User> find(){
		
		return this.find(User.class);	
	}
}
