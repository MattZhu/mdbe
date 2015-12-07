package com.mdaedu.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import com.mdaedu.domainobject.Charpater;

@Component
public class ChapterDao extends BaseDao<Charpater> {
	
	@SuppressWarnings("unchecked")
	public List<Charpater> getChapterByCourse(Integer courseId, boolean initChildren){
		List<Charpater> chs=getHibernateTemplate().findByNamedQuery("byCourse", courseId);
			
		initialize(chs);
	
		return chs;
	}
	
	@SuppressWarnings("unchecked")
	public List<Charpater> getChapterByCourseWithoutParent(Integer courseId,boolean initChildren){
		List<Charpater> chs=getHibernateTemplate().findByNamedQuery("byCourseWithoutParent", courseId);
			
		initialize(chs);
	
		return chs;
	}
	
	private void initialize(Collection<Charpater> chs){
//		for(Charpater ch:chs){
//			 Hibernate.initialize(ch.getChildren());
//			 if(!ch.getChildren().isEmpty()){
//				 initialize(ch.getChildren());
//			 }
//		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Charpater> getChapterByParent(Integer pId,Integer courseId){
		if(pId!=null){
			List<Charpater> chs=getHibernateTemplate().findByNamedQuery("byParent", pId);
			initialize(chs);
			return chs;
		}else{
			return getChapterByCourse(courseId,false);
		}
	}
}
