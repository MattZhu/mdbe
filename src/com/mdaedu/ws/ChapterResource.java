package com.mdaedu.ws;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.springframework.beans.factory.annotation.Autowired;

import com.mdaedu.domainobject.Charpater;
import com.mdaedu.domainobject.Course;
import com.mdaedu.services.ChapterService;
import com.mdaedu.services.QuestionService;
import com.mdaedu.services.VideoService;
import com.mdaedu.ws.message.CharpterResponse;
import com.mdaedu.ws.message.Question;
import com.mdaedu.ws.message.VideoMsg;


@RequestScoped
@Path("/chapters")
public class ChapterResource {

	@Autowired
	private ChapterService service;
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private QuestionService questionService;
	@GET
	@Produces({ "application/json;charset=UTF-8" })
	public MyResponse<Charpater> getCourese(@Context UriInfo info) {	
		String courseId = info.getQueryParameters().getFirst("cId");
		String to = info.getQueryParameters().getFirst("to");
		String page= info.getQueryParameters().getFirst("page");
		String rows= info.getQueryParameters().getFirst("rows");
		MyResponse<Charpater> result=new MyResponse<Charpater>();
		result.setPage(Integer.valueOf(page));
		result.setTotal(2);
		result.setRecords(1);
		result.setRows(this.service.getChapterByCourseWithoutParent(Integer.valueOf(courseId),false));
		return result;
	}
	
	@GET
	@Path("/children")
	@Produces({ "application/json;charset=UTF-8" })
	public MyResponse<Charpater> getCoureseForChildren(@Context UriInfo info) {	
		String pId = info.getQueryParameters().getFirst("pId");
		MyResponse<Charpater> result=new MyResponse<Charpater>();
		result.setPage(1);
		result.setTotal(2);
		result.setRecords(1);
		List<Charpater> chs=this.service.getChapterByParent(Integer.valueOf(pId),null);
		
		result.setRows(chs);
		return result;
	}
	
	
	@GET
	@Produces({ "application/json;charset=UTF-8" })
	@Path("/clients")
	public CharpterResponse getCoureseForMobile(@Context UriInfo info) {	
		Integer courseId = Integer.valueOf(info.getQueryParameters().getFirst("cId"));
		CharpterResponse result=new CharpterResponse();
		List<Charpater>chs=service.getChapterByCourse(courseId,true);
		List<com.mdaedu.ws.message.Charpater> chRes=new ArrayList<com.mdaedu.ws.message.Charpater>();
		List<VideoMsg> videos=videoService.getByCourse(courseId);
		List<Question> questions=questionService.getQuestionsByCourse(courseId);
		for(;chs.size()>0;){
			Charpater ch=chs.get(0);
			com.mdaedu.ws.message.Charpater newCh=new com.mdaedu.ws.message.Charpater();
			newCh.copyFrom(ch);
			setVideosAndQuestions(newCh,videos,questions);
			
			if(ch.getParent()==null){
				chRes.add(newCh);
				chs.remove(0);
			}else{
				com.mdaedu.ws.message.Charpater p=findParent(chRes,ch.getParent());
				if(p!=null){
					p.addChild(newCh);
					chs.remove(0);
				}
			}
		}
		
		result.setCharpaters(chRes.toArray(new com.mdaedu.ws.message.Charpater[0]));
		return result;
	}
	
	
	
	private void setVideosAndQuestions(com.mdaedu.ws.message.Charpater newCh, List<VideoMsg> videos,List<Question>questions) {
		Iterator<VideoMsg> it=videos.iterator();
		List<VideoMsg> result=new ArrayList<VideoMsg>();
		while( it.hasNext()){
			VideoMsg v=it.next();
			if(v.getChId().equals(newCh.getId())){
				result.add(v);
				it.remove();
			}
		}
		if(result.size()>0){
			newCh.setVideos(result);
		}
		List<Question> qs=new ArrayList<Question>();
		Iterator <Question> qIt=questions.iterator();
		while(qIt.hasNext()){
			Question q=qIt.next();
			if(q.getChId().equals(newCh.getId())){
				qs.add(q);
			}
		}
		if(qs.size()>0){
			newCh.setQuestions(qs);
		}
	}

	private com.mdaedu.ws.message.Charpater findParent(
			Collection<com.mdaedu.ws.message.Charpater> chRes, Charpater parent) {
		com.mdaedu.ws.message.Charpater result=null;
		for(com.mdaedu.ws.message.Charpater ch:chRes){
			if(ch.getId()==parent.getId()){
				return ch;
			}else if(ch.getChildren()!=null){
				result=findParent(ch.getChildren(),parent);
				if(result!=null)
				{
					return result;
				}
			}
			
		}
		return null;
	}

	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces({"application/json;charset=UTF-8"})
	public Charpater saveOrUpdate(@QueryParam("cId")Integer courseId,@QueryParam("pId")Integer pId,@FormParam("oper")String oper,@FormParam("id")String id,@FormParam("name")String name){
		
//		System.out.println("id:"+id+",name:"+name+",cid:"+courseId+",oper"+oper);
		Charpater ch=new Charpater();
		if(courseId!=null){
			Course course=new Course();
			course.setId(courseId);
			ch.setCourse(course);
		}
		if(pId!=null){
			Charpater parent=new Charpater();
			parent.setId(pId);
			ch.setParent(parent);
		}
		if(oper.equals("edit")){			
			ch.setId(Integer.valueOf(id));
			ch.setName(name);
			service.saveOrUpdate(ch);
		}else if(oper.equals("add")){
			ch.setName(name);
			service.saveOrUpdate(ch);
		}else if(oper.equals("del")){
			ch.setId(Integer.valueOf(id));
			service.delete(ch);
		}
		return ch;
	}
	
}
