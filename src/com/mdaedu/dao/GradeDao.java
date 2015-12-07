package com.mdaedu.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import com.mdaedu.domainobject.Grade;

@Component
public class GradeDao extends BaseDao<Grade> {
	
	
	public List<Grade> findAll(){
		return this.getHibernateTemplate().loadAll(Grade.class);
	}
	
	public Grade find(Integer id){
		Grade g=this.getHibernateTemplate().load(Grade.class, id);
//		Hibernate.initialize(g);
		Grade r=new Grade();
		try {
			BeanUtils.copyProperties(r, g);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}

}
