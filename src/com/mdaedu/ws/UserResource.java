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

import com.mdaedu.domainobject.Subject;
import com.mdaedu.domainobject.User;
import com.mdaedu.services.UserServices;

@RequestScoped
@Path("/users")
public class UserResource {
	
	@Autowired
	private UserServices userService;
    private User u;
	public UserResource() {
	}
	
	@POST
	@Path("/login")
	@Consumes("application/x-www-form-urlencoded")
	@Produces({"application/json;charset=UTF-8"})
	public User authenticate(@FormParam("username")String username,@FormParam("password")String password){
		System.out.println("username:"+username+",password:"+password);
		u = userService.authenticte(username, password);
		return u;
	}
	@GET
	@Produces({"application/json;charset=UTF-8"})
	public List<User> getList() {	
		
		return userService.getAll();
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces({"application/json;charset=UTF-8"})
	public User saveOrUpdate(@FormParam("oper")String oper,
			@FormParam("id")String id,
			@FormParam("userName")String userName,
			@FormParam("password")String password,
			@FormParam("role")String role
			){
		
		System.out.println("username:"+userName+",password:"+password+"role:"+role);
		User g=new User();
		if(oper.equals("edit")){
			g.setId(Integer.valueOf(id));
			g.setPassword(password);
			g.setUserName(userName);
			g.setRole(role);
//			g.setSeq(seq);
			userService.saveOrUpdate(g);
		}else if(oper.equals("add")){
			g.setPassword(password);
			g.setUserName(userName);
			g.setRole(role);
			userService.saveOrUpdate(g);
		}else if(oper.equals("del")){
			g.setId(Integer.valueOf(id));
			userService.delete(g);
		}
		return g;
	}
}
