package com.mdaedu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdaedu.dao.SubjectDao;
import com.mdaedu.domainobject.Subject;

@Transactional
@Service
public class SubjectService {
	@Autowired
	private SubjectDao dao;
	

	public List<Subject> getAll(){
		return dao.findAll();
	}


	public void saveOrUpdate(Subject s) {
		dao.saveOrUpdate(s);		
	}


	public void delete(Subject s) {
		dao.delete(s);
	}
	
	
}
