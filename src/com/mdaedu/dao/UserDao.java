package com.mdaedu.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mdaedu.domainobject.User;
@Component
public class UserDao extends BaseDao<User> {
	public User findByUsernameAndPassword(String username,String password){
		List<User> users=this.getHibernateTemplate().findByNamedQuery("byNameAndPassword",username,password);
		if(users.size()==1){
			return users.get(0);
		}else{
			return null;
		}
	}
	
	public List<User> findAll(){
		List<User> result= new ArrayList<User>();
		List<User> dbresult=this.getHibernateTemplate().loadAll(User.class);
		for(User u:dbresult){
			User user=new User();
			user.setId(u.getId());
			user.setPassword(u.getPassword());
			user.setUserName(u.getUserName());
			user.setRole(u.getRole());
			result.add(user);
		}
		return result;
	}
}
