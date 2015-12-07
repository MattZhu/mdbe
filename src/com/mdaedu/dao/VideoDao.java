package com.mdaedu.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mdaedu.domainobject.Video;

@Component
public class VideoDao extends BaseDao<Video> {

	@SuppressWarnings("unchecked")
	public List<Video> getVideoByCh(Integer chId) {
		 List<Video> result=this.getHibernateTemplate().findByNamedQuery("byCharpter",chId);
		 return result;
	}
	public List<Video> getVideoByCourse(Integer courseId){
		 List<Video> result=this.getHibernateTemplate().findByNamedQuery("videosByCourse",courseId);
		 return result;		
	}
}
