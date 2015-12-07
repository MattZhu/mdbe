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
@Table(name = "questions")
@NamedQueries({
	@NamedQuery(name = "byChId", query = "from Questions q where q.charpater.id=?"),
	@NamedQuery(name = "questionsByCourseId", query = "from Questions q where q.charpater.course.id=?")
	
})
public class Questions {

	public Questions() {
		// TODO Auto-generated constructor stub
	}
	
	
	private Integer id;
	
	private String title;
	
	private String type;
	
	private String options;
	
	private String correctAnswer;
	
	private String imagePath;
	
	private String explaination;
	
	private Charpater charpater;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	

	/**
	 * @return the explaination
	 */
	public String getExplaination() {
		return explaination;
	}

	/**
	 * @param explaination the explaination to set
	 */
	public void setExplaination(String explaination) {
		this.explaination = explaination;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ch_id")
	public Charpater getCharpater() {
		return charpater;
	}

	public void setCharpater(Charpater charpater) {
		this.charpater = charpater;
	}
	
	
	
}
