package com.zh.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.zh.dao.UserDao;
import com.zh.entity.User;

@Service
public class UserService {
	
	@Resource
	private UserDao userDao;
	
	public boolean save(User user){
		
		if(userDao.save(user)!=null)return true;
		return false;
	}
	public void delete(User user){
		userDao.delete(user);
	}
	
	public User get(int id){
		return userDao.get(User.class, id);
	}
	public List<User> find(){
		return userDao.find();
	}
	
	public List<User> find(String hql){
		return userDao.find(hql);
	}
	
	public List<User> find(String hql, Object... objects){
		return userDao.find(hql,objects);
	}
	
	public List<User> find(String column, String value){
		return userDao.find("from User where "+column+" like '%"+value+"%'");
	}
	
	public List<User> find(DetachedCriteria criteria,int firstResult, int maxResults){
		return userDao.find(criteria, firstResult, maxResults);
	}
	
	public void deleteAll(){
		userDao.sqlQuery("truncate table t_user");
	}
	
	public void update(User user){
		userDao.update(user);
	}

}
