package com.mdaedu.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mdaedu.domainobject.Publisher;

@Component
public class PublisherDao extends BaseDao<Publisher> {

	public List<Publisher> findAll(){
		return this.getHibernateTemplate().loadAll(Publisher.class);
	}
	
	public Publisher find(Integer id){
		Publisher publisher=getHibernateTemplate().load(Publisher.class, id);
		return publisher;
	}
}
