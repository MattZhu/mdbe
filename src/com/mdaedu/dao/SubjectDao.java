package com.mdaedu.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mdaedu.domainobject.Subject;

@Component
public class SubjectDao extends BaseDao<Subject> {
	
	public List<Subject> findAll(){
		return this.getHibernateTemplate().loadAll(Subject.class);
	}
	
	public Subject find(Integer id){
		Subject s=this.getHibernateTemplate().load(Subject.class, id);
		return s;
	}
}
