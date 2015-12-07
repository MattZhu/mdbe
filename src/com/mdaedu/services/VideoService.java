package com.mdaedu.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdaedu.dao.VideoDao;
import com.mdaedu.domainobject.Video;
import com.mdaedu.ws.message.VideoMsg;

@Transactional
@Service
public class VideoService {
	@Autowired
	private VideoDao dao;
	
	public void saveOrUpdate(Video v){
		dao.saveOrUpdate(v);
	}
	
	public Video getVideo(Video v){
		return dao.get(v, v.getId());
	}
	public void deleteVideo(Video v){
		dao.delete(v);
	}
	
	public List<Video> getVideoByCh(Integer chId){
		List<Video> result=new ArrayList<Video>();
		for(Video v:dao.getVideoByCh(chId)){
			Video nVideo=new Video();
			nVideo.setId(v.getId());
			nVideo.setName(v.getName());
			nVideo.setSavedName(v.getSavedName());
			result.add(nVideo);
		}
		return result;
	}
	
	public List<VideoMsg> getByCourse(Integer courseId){
		List<VideoMsg> result=new ArrayList<VideoMsg>();
		for(Video v:dao.getVideoByCourse(courseId)){
			VideoMsg nVideo=new VideoMsg();
			nVideo.setId(v.getId());
			nVideo.setName(v.getName());
			nVideo.setSavedName(v.getSavedName());
			nVideo.setChId(v.getCharpater().getId());
			result.add(nVideo);
		}
		return result;
	}
}
