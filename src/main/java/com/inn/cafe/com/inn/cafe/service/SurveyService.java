package com.inn.cafe.com.inn.cafe.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.inn.cafe.com.inn.cafe.POJO.Question;
import com.inn.cafe.com.inn.cafe.POJO.SubmittedSurveyReq;
import com.inn.cafe.com.inn.cafe.POJO.Survey;
import com.inn.cafe.com.inn.cafe.POJO.SurveyDTO;
import com.inn.cafe.com.inn.cafe.POJO.SurveyWrapper;
import com.inn.cafe.com.inn.cafe.POJO.User;

public interface SurveyService {
    ResponseEntity<String> createSurvey(Survey survey);
    ResponseEntity<String> createQuestion(Question question);
    ResponseEntity<List<Survey>> getSurveys();
    List<SurveyDTO> getSurveysDTO();
    SurveyWrapper getSurvey(int id);
    ResponseEntity<Question> addQuestion(int questionId, int surveyId);
    ResponseEntity<String> deleteSurvey(int id);
    ResponseEntity<String> submitSurvey(SubmittedSurveyReq survey);
    SurveyWrapper getSubmittedSurvey(int id);
}
