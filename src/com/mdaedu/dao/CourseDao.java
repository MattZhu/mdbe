package com.mdaedu.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mdaedu.domainobject.Course;
import com.mdaedu.domainobject.Subject;

@Component
public class CourseDao extends BaseDao<Course>{

	@SuppressWarnings("unchecked")
	public List<Course> getCourse(Integer subjectId){
		
		return this.getHibernateTemplate().findByNamedQuery("courseBySubject", subjectId);
	
	}
	
	public Course getCourseById(Integer courseId){
		return this.get(new Course(),courseId);
	}
	
	public List<Course> getCourse(){
		return this.getHibernateTemplate().loadAll(Course.class);	
	}
}
