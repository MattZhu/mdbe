package com.mdaedu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdaedu.dao.ChapterDao;
import com.mdaedu.dao.CourseDao;
import com.mdaedu.dao.VideoDao;
import com.mdaedu.domainobject.Charpater;

@Transactional
@Service
public class ChapterService {
	@Autowired
	private ChapterDao dao;
	
	@Autowired
	private CourseDao courseDao;
	
	
	public List<Charpater> getChapterByCourse(Integer courseId,boolean initChildren){
		List<Charpater> result=dao.getChapterByCourse(courseId,initChildren);
		return result;
	}
	public List<Charpater> getChapterByCourseWithoutParent(Integer courseId,boolean initChildren){
		List<Charpater> result=dao.getChapterByCourseWithoutParent(courseId,initChildren);
		return result;
	}

	public List<Charpater> getChapterByParent(Integer pId,Integer courseId){
		return dao.getChapterByParent(pId, courseId);
	}
	
	public Charpater getById(Integer id){		
		return dao.get(new Charpater(), id);
	}
	
	public void saveOrUpdate(Charpater ch){
//		
		if(ch.getCourse()==null){
			Charpater p=this.dao.get(ch.getParent(), ch.getParent().getId());
			ch.setCourse(p.getCourse());
		}
		dao.saveOrUpdate(ch);
	}
	
	public void delete(Charpater ch){
		dao.delete(ch);
	}
}
