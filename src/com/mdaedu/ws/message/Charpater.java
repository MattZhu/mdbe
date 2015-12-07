package com.mdaedu.ws.message;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class Charpater {
	public Charpater() {
//		children=new ArrayList<Charpater>();
	}

	public void copyFrom(com.mdaedu.domainobject.Charpater ch){
		this.id=ch.getId();
		this.name=ch.getName();
//		this.course=ch.getCourse();
//		this.video=ch.getVideo();
	}
	
	public void addChild(Charpater ch){
		if(this.children==null){
			children=new ArrayList<Charpater>();
		}
		this.children.add(ch);
	}
	
	private Integer id;
	
	private String name;
	
	
//	private Course course;
	
	private List<Charpater> children;
	
	private List<VideoMsg> videos;
	
	private List<Question> questions;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public Course getCourse() {
//		return course;
//	}
//
//	public void setCourse(Course course) {
//		this.course = course;
//	}

	public List<VideoMsg> getVideos() {
		return videos;
	}

	public void setVideos(List<VideoMsg> videos) {
		this.videos = videos;
	}

	public List<Charpater> getChildren() {
		return children;
	}

	public void setChildren(List<Charpater> children) {
		this.children = children;
	}

	/**
	 * @return the questions
	 */
	public List<Question> getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	
	
}
