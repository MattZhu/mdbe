package com.mdaedu.domainobject;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "video")
@NamedQueries({
	@NamedQuery(name = "byCharpter", query = "from Video v where v.charpater.id=?"),
	@NamedQuery(name = "videosByCourse", query = "from Video v where v.charpater.course.id=?")
})
public class Video {

	private Integer id;
	
	private String name;
	
	private String savedName;
	
	private Charpater charpater;
	
	public Video() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public String getSavedName() {
		return savedName;
	}

	public void setSavedName(String savedName) {
		this.savedName=savedName;
	}

	/**
	 * @return the charpater
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ch_id")
	public Charpater getCharpater() {
		return charpater;
	}

	/**
	 * @param charpater the charpater to set
	 */
	public void setCharpater(Charpater charpater) {
		this.charpater = charpater;
	}
	
	

}
