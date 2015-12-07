package com.mdaedu.domainobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "charpater")
@NamedQueries({
	@NamedQuery(name = "byCourseWithoutParent", query = "from Charpater ch where ch.course.id=? and ch.parent is null"),
	@NamedQuery(name = "byCourse", query = "from Charpater ch where ch.course.id=?"),
	@NamedQuery(name = "byParent", query = "from Charpater ch where ch.parent.id=?")
})
public class Charpater {

	public Charpater() {
		// TODO Auto-generated constructor stub
	}

	
	private Integer id;
	
	private String name;
	
	private Charpater parent;
	
	private Course course;
	
//	private Set<Charpater> children;
	
//	private Video video;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "charpater_id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "charpater_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@ManyToOne
	@JoinColumn(name = "parent_id")
	public Charpater getParent() {
		return parent;
	}

	public void setParent(Charpater parent) {
		this.parent = parent;
	}
	@ManyToOne
	@JoinColumn(name = "course_id")
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
//	@ManyToOne
//	@JoinColumn(name = "video_id")
//	public Video getVideo() {
//		return video;
//	}
//
//	public void setVideo(Video video) {
//		this.video = video;
//	}
//	@OneToMany 
//	public Set<Charpater> getChildren() {
//		return children;
//	}
//
//	public void setChildren(Set<Charpater> children) {
//		this.children = children;
//	}
	
	
	
	
	
}
