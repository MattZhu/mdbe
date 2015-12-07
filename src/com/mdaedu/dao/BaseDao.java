package com.mdaedu.dao;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class BaseDao  <T> extends HibernateDaoSupport{

	
	public BaseDao( ) {
		
	}
	
	@Autowired
	public void setSF(SessionFactory sessionFactory){  
		  super.setSessionFactory(sessionFactory);  
	}  
	
	
	public void saveOrUpdate(T c){
		this.getHibernateTemplate().saveOrUpdate(c);
	}	
	
	public T get(T c,Serializable id){
		 this.getHibernateTemplate().load(c, id);
		 return c;
	}
	
	public void delete(T c){
		this.getHibernateTemplate().delete(c);
	}
}
