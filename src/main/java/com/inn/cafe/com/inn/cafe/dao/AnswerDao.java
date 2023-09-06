package com.inn.cafe.com.inn.cafe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inn.cafe.com.inn.cafe.POJO.Answer;
import com.inn.cafe.com.inn.cafe.POJO.Question;

public interface AnswerDao extends JpaRepository<Answer, Integer> {
    List<Answer> findByQuestionsIn(List<Question> questions);
}
