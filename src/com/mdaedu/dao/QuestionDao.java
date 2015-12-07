package com.mdaedu.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mdaedu.domainobject.Questions;
import com.mdaedu.ws.message.Question;

@Component
public class QuestionDao extends BaseDao<Questions> {
	@SuppressWarnings("unchecked")
	public List<Question> findByChapterId(Integer chId){
		List<Questions> questions= getHibernateTemplate().findByNamedQuery("byChId", chId);
		return  convert(questions);
	}
	
	@SuppressWarnings("unchecked")
	public List<Question> findByCourseId(Integer courseId){
		List<Questions> questions=getHibernateTemplate().findByNamedQuery("questionsByCourseId", courseId);
		List<Question> result = convert(questions);
		return result; 
	}

	private List<Question> convert(List<Questions> questions) {
		List<Question> result=new ArrayList<Question>();
		for(Questions q:questions){
			Question newQ=new Question();
			newQ.setChId(q.getCharpater().getId());
			newQ.setCorrectAnswer(q.getCorrectAnswer());
			newQ.setId(q.getId());
			newQ.setImagePath(q.getImagePath());
			newQ.setOptions(q.getOptions());
			newQ.setTitle(q.getTitle());
			newQ.setType(q.getType());
			newQ.setExplaination(q.getExplaination());
			result.add(newQ);
		}
		return result;
	}
}
