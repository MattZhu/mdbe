package com.mdaedu.ws;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.springframework.beans.factory.annotation.Autowired;

import com.mdaedu.domainobject.Grade;
import com.mdaedu.services.GradeService;
import com.mdaedu.ws.message.GradeResponse;

@RequestScoped
@Path("/grades")
public class GradeUI {
	
	@Autowired
	private GradeService service;
	public GradeUI() {
	
	}

	@GET
	@Produces({"application/json;charset=UTF-8"})
	public List<Grade> getList() {	
		
		return service.getAll();
	}
	
	@GET
	@Path("/clients")
	@Produces({"application/json;charset=UTF-8"})
	public GradeResponse getListForMobile() {	
		GradeResponse response=new GradeResponse();
		response.setGrades(service.getAll().toArray(new Grade[0]));
		return response;
	}
	
	@GET
	@Path("/dropdown")
	@Produces({"application/json;charset=UTF-8"})
	public String getDropdownList() {	
		 StringBuilder sb=new StringBuilder();
		 sb.append("<select>");
		List<Grade> grades=service.getAll();
		for(Grade g:grades)
		{
			 sb.append("<option value=\"");
			 sb.append(g.getId());
			 sb.append("\">");
			 sb.append( g.getName());
			 sb.append("</option>");
		} 
		sb.append("</select>");
		return sb.toString();
	}
	
	@GET
	@Path("/{id}")
	@Produces({"application/json;charset=UTF-8"})
	public Grade myGrade(@PathParam("id") int id) {
		return service.getById(id);
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces({"application/json;charset=UTF-8"})
	public Grade saveOrUpdate(@FormParam("oper")String oper,@FormParam("id")String id,@FormParam("name")String name,@FormParam("seq")Integer seq){
		System.out.println("id:"+id+",gradeId:"+name+",subjectId:"+seq+"");
	
		Grade g=new Grade();
		if(oper.equals("edit")){
			g.setId(Integer.valueOf(id));
			g.setName(name);
			g.setSeq(seq);
			service.saveOrUpdate(g);
		}else if(oper.equals("add")){
			g.setName(name);
			g.setSeq(seq);
			service.saveOrUpdate(g);
		}else if(oper.equals("del")){
			g.setId(Integer.valueOf(id));
			service.delete(g);
		}
		return g;
	}
}
