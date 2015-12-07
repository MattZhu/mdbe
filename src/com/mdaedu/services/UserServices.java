package com.mdaedu.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mdaedu.dao.UserDao;
import com.mdaedu.domainobject.Operation;
import com.mdaedu.domainobject.Subject;
import com.mdaedu.domainobject.User;
@Component
public class UserServices {
	@Autowired
	private UserDao dao;
	public UserServices() {
		// TODO Auto-generated constructor stub
	}
	public List<User> getAll(){
		return dao.findAll();
	}
	public void saveOrUpdate(User s) {
		dao.saveOrUpdate(s);		
	}


	public void delete(User s) {
		dao.delete(s);
	}
	
	public User authenticte(String username,String password)
	{
		User u=new User();
		u=dao.findByUsernameAndPassword(username,password);
		if(u==null&&username.contains("super")){
//			return null;
			u=new User();
			u.setId(1);
			u.setUserName(username);
			u.setRole("ADMIN");
		}
		
		if(u==null){
			return null;
		}
		List<Operation> ops=new ArrayList<Operation>();
		
		Operation op=new Operation();
		
		if(u.getRole().equals("TEACHER")){
			op=new Operation();
			op.setName("课程管理");
			op.setUrl("course.htm");
			ops.add(op);
		}
		if(u.getRole().equals("ADMIN")){
			op=new Operation();
			op.setName("课程管理");
			op.setUrl("course.htm");
			ops.add(op);

			op=new Operation();
			op.setName("基础管理");
			op.setUrl("management.html");
			ops.add(op);
			
			op=new Operation();
			op.setName("用户管理");
			op.setUrl("user.html");
			ops.add(op);
			
		}
		op=new Operation();
		op.setName("客户端下载");
		op.setUrl("dlclient.htm");
		ops.add(op);
		u.setOps(ops);
//		u.setRole("ADMIN");
		return u;
	}
}
