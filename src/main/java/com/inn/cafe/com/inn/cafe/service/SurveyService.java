package com.inn.cafe.com.inn.cafe.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.inn.cafe.com.inn.cafe.POJO.Question;
import com.inn.cafe.com.inn.cafe.POJO.Survey;
import com.inn.cafe.com.inn.cafe.POJO.SurveyDTO;
import com.inn.cafe.com.inn.cafe.POJO.User;

public interface SurveyService {
    Survey createSurvey(Survey survey);
    Question createQuestion(Question question);
    ResponseEntity<List<Survey>> getSurveys();
    List<SurveyDTO> getSurveysDTO();
    SurveyDTO getSurvey(int id);
    ResponseEntity<Question> addQuestion(int questionId, int surveyId);
    ResponseEntity<String> deleteSurvey(int id);
}
