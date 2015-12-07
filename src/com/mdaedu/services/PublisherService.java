package com.mdaedu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdaedu.dao.PublisherDao;
import com.mdaedu.domainobject.Publisher;

@Transactional
@Service
public class PublisherService {
	@Autowired
	private PublisherDao dao;
	
	public List<Publisher> getAll(){
		return dao.findAll();
	}
	public Publisher getById(Integer id){
		return dao.find(id);
	}
	
	public void saveOrUpdate(Publisher g){
		dao.saveOrUpdate(g);
	}
	
	public void delete(Publisher g)
	{
		dao.delete(g);
	}
}
