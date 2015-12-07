package com.mdaedu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdaedu.dao.GradeDao;
import com.mdaedu.domainobject.Grade;
@Transactional
@Service
public class GradeService {

	@Autowired
	private GradeDao dao;
	
	public List<Grade> getAll(){
		return dao.findAll();
	}
	
	public Grade getById(Integer id){
		return dao.find(id);
	}
	
	public void saveOrUpdate(Grade g){
		dao.saveOrUpdate(g);
	}
	
	public void delete(Grade g)
	{
		dao.delete(g);
	}
}
