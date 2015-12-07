package com.mdaedu.ws;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.springframework.beans.factory.annotation.Autowired;

import com.mdaedu.domainobject.Grade;
import com.mdaedu.domainobject.Subject;
import com.mdaedu.services.SubjectService;

@RequestScoped
@Path("/subjects")
public class SubjectResource {

	@Autowired
	private SubjectService service;
	@GET
	@Path("/dropdown")
	@Produces({"application/json;charset=UTF-8"})
	public String getDropdownList() {	
		 StringBuilder sb=new StringBuilder();
		 sb.append("<select>");
		 List<Subject>subjects=service.getAll();
		 for(Subject s:subjects){
			 sb.append("<option value=\"");
			 sb.append(s.getId());
			 sb.append("\">");
			 sb.append( s.getName());
			 sb.append("</option>");
		 }
		 sb.append("</select>");
		 return sb.toString();
	}
	
	@GET
	@Produces({"application/json;charset=UTF-8"})
	public List<Subject> getList() {	
		
		return service.getAll();
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces({"application/json;charset=UTF-8"})
	public Subject saveOrUpdate(@FormParam("oper")String oper,@FormParam("id")String id,@FormParam("name")String name,@FormParam("seq")Integer seq){
		System.out.println("id:"+id+",gradeId:"+name+",subjectId:"+seq+"");
	
		Subject g=new Subject();
		if(oper.equals("edit")){
			g.setId(Integer.valueOf(id));
			g.setName(name);
//			g.setSeq(seq);
			service.saveOrUpdate(g);
		}else if(oper.equals("add")){
			g.setName(name);
//			g.setSeq(seq);
			service.saveOrUpdate(g);
		}else if(oper.equals("del")){
			g.setId(Integer.valueOf(id));
			service.delete(g);
		}
		return g;
	}
}
