package com.mdaedu.domainobject;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "user")
@NamedQueries({
	@NamedQuery(name = "byNameAndPassword", query = "from User u where u.userName=? and u.password=?")
})
public class User {

	public User() {
		// TODO Auto-generated constructor stub
	}
	
	private Integer id;
	
	private String userName;
	
	private String password;
	
	private String role;
	
	private List<Operation> ops;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	 @ManyToMany
	 @JoinTable(name="user_ops")
	public List<Operation> getOps() {
		return ops;
	}

	public void setOps(List<Operation> ops) {
		this.ops = ops;
	}
	
	
	
	
}
