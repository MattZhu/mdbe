package com.mdaedu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdaedu.dao.CourseDao;
import com.mdaedu.dao.GradeDao;
import com.mdaedu.dao.PublisherDao;
import com.mdaedu.dao.SubjectDao;
import com.mdaedu.domainobject.Course;
import com.mdaedu.domainobject.Grade;
import com.mdaedu.domainobject.Publisher;
import com.mdaedu.domainobject.Subject;

@Transactional
@Service
public class CourseService {
	@Autowired
	private CourseDao dao;
	
	@Autowired
	private SubjectDao subjectDao;
	
	@Autowired
	private GradeDao gradeDao;
	
	@Autowired
	private PublisherDao publisherDao;
	
	public List<Course> getCourse(Integer subjectId){
		List<Course> result=dao.getCourse(subjectId);
		
		return result;
	}
	
	public Course getCourseById(Integer courseId){
		return dao.getCourseById(courseId);
	}
	
	public List<Course> getCourse(){
		List<Course> result=dao.getCourse();
		
		return result;
	}
	
	public void saveOrUpdate(Course course)
	{
		Grade g=gradeDao.find(course.getGrade().getId());
		Subject s=subjectDao.find(course.getSubject().getId());
//		Publisher p=publisherDao.find(course.getPublisher().getId());
		course.setName(g.getName()+"-"+s.getName());
		this.dao.saveOrUpdate(course);
	}
	
	public void delete(Course course)
	{
		this.dao.delete(course);
	}
}
