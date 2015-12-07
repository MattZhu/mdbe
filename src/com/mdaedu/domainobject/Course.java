package com.mdaedu.domainobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "course")
@NamedQuery(name = "courseBySubject", query = "from Course c where c.subject.id=?")
public class Course {

	public Course() {
		// TODO Auto-generated constructor stub
	}

	private Integer id;
	
	private String name;
	
	private String imagePath;
	
	private Subject subject;
	private Grade grade;
	
	private Publisher publisher;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
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
	@ManyToOne
	@JoinColumn(name = "subject_id")
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	@ManyToOne
	@JoinColumn(name = "grade_id")
	public Grade getGrade() {
		return grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	/**
	 * @return the publisher
	 */
	@ManyToOne
	@JoinColumn(name = "publisher_id")
	public Publisher getPublisher() {
		return publisher;
	}
	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}
	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
	
}
