package com.mdaedu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdaedu.dao.ChapterDao;
import com.mdaedu.dao.QuestionDao;
import com.mdaedu.domainobject.Charpater;
import com.mdaedu.domainobject.Questions;
import com.mdaedu.ws.message.Question;

@Transactional
@Service
public class QuestionService {
	@Autowired
	private QuestionDao dao;
	
	@Autowired
	private ChapterDao chDao;
	
	public Questions getQuestionsId(Integer id){
		Questions q=new Questions();
		q=dao.get(q, id);
		return q;
	}
	
	public List<Question> getQuestionsByChId(Integer chId){
		List<Question> result=dao.findByChapterId(chId);
		
		return result;
	}

	public void saveOrUpdate(Questions q) {
		q.setCharpater(chDao.get(new Charpater(),q.getCharpater().getId()));
		 dao.saveOrUpdate(q);
	}
	
	public void deleteQuestion(Questions q){
		dao.delete(q);
	}

	public List<Question> getQuestionsByCourse(Integer courseId) {
		List<Question> result=dao.findByCourseId(courseId);
		return result;
	}
}
