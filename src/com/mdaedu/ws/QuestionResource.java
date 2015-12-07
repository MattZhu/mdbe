package com.mdaedu.ws;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.springframework.beans.factory.annotation.Autowired;

import com.mdaedu.domainobject.Charpater;
import com.mdaedu.domainobject.Questions;
import com.mdaedu.services.QuestionService;
import com.mdaedu.utils.AppUtils;
import com.mdaedu.ws.message.Question;

@RequestScoped
@Path("/questions")
public class QuestionResource {
	@Autowired
	private QuestionService service;
	@GET
	@Produces({"application/json;charset=UTF-8"})
	public List<Question> getQuestionsByChId(@QueryParam("chId")Integer chId){
		return service.getQuestionsByChId(chId);
	}
	
	@POST
	@Path("/image")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadImage(
			@FormDataParam("qId")Integer qId,
			@FormDataParam("imageFile") InputStream fileInputStream,
			@FormDataParam("imageFile") FormDataContentDisposition contentDispositionHeader) {
		File folder=AppUtils.getImageFolder();
		System.out.println("qId:"+qId);
		String output = "File saved to server location : " ;
		Questions q=service.getQuestionsId(qId);
		String savedName= q.getId()+contentDispositionHeader.getFileName().substring(contentDispositionHeader.getFileName().lastIndexOf("."));;
		q.setImagePath(savedName);
		service.saveOrUpdate(q);
		File filePath = new File(folder,savedName);
		AppUtils.saveFile(fileInputStream, filePath);
		return Response.status(200).entity(output).build();
	}
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces({"application/json;charset=UTF-8"})
	public Questions saveOrUpdate(@FormParam("chId")Integer chid,@FormParam("qtitle")String qtitle,
			@FormParam("qtype")String qtype,
			@FormParam("answer")String answer,
			@FormParam("qid")Integer qid,
			@FormParam("qexplaination") String qexplaination,
			@FormParam("options")List<String> options,@FormParam("cOptions")List<String> cOptions){
	
		Questions q=new Questions();
		if(qid!=null){
			q.setId(qid);
		}
		q.setTitle(qtitle);
		q.setExplaination(qexplaination);
		Charpater charpater=new Charpater();
		charpater.setId(chid);
		q.setCharpater(charpater);
		
		String cAnswer="";
		for(String cpt:cOptions){
			cAnswer+=cpt+":=;=:";
		}
		if(cAnswer.length()>5){
			cAnswer=cAnswer.substring(0,cAnswer.lastIndexOf(":=;=:"));
		}
//		System.out.println(cAnswer);
		q.setCorrectAnswer(cAnswer);
		q.setType(qtype);
		String option="";
		for(String op:options){
			option+=op+":=;=:";
		}
		if(option.length()>5){
			option=option.substring(0,option.lastIndexOf(":=;=:"));
		}
		q.setOptions(option);
		
		service.saveOrUpdate(q);
		return q;
	}
	
	@GET
	@Path("/del")
	@Produces({"application/json;charset=UTF-8"})
	public Questions delete(@QueryParam("qid")Integer qid){
	
		Questions q=new Questions();
		q.setId(qid);
		service.deleteQuestion(q);
		return q;
	}
}
