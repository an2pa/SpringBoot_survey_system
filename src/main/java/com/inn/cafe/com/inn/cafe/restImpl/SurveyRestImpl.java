package com.inn.cafe.com.inn.cafe.restImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafe.com.inn.cafe.POJO.Answer;
import com.inn.cafe.com.inn.cafe.POJO.Question;
import com.inn.cafe.com.inn.cafe.POJO.QuestionAnswer;
import com.inn.cafe.com.inn.cafe.POJO.SubmittedSurvey;
import com.inn.cafe.com.inn.cafe.POJO.SubmittedSurveyReq;
import com.inn.cafe.com.inn.cafe.POJO.Survey;
import com.inn.cafe.com.inn.cafe.POJO.SurveyDTO;
import com.inn.cafe.com.inn.cafe.POJO.SurveyWrapper;
import com.inn.cafe.com.inn.cafe.POJO.User;
import com.inn.cafe.com.inn.cafe.dao.AnswerDao;
import com.inn.cafe.com.inn.cafe.dao.QuestionDao;
import com.inn.cafe.com.inn.cafe.dao.SubmittedSurveyDao;
import com.inn.cafe.com.inn.cafe.dao.SurveyDao;
import com.inn.cafe.com.inn.cafe.rest.SurveyRest;
import com.inn.cafe.com.inn.cafe.rest.UserRest;
import com.inn.cafe.com.inn.cafe.service.SurveyService;
import com.inn.cafe.com.inn.cafe.service.UserService;
import com.inn.cafe.com.inn.cafe.utils.CafeUtils;

@RestController
public class SurveyRestImpl implements SurveyRest {

    @Autowired
    SurveyService surveyService;

    @Autowired
    QuestionDao questionDao;

    @Autowired
    AnswerDao answerDao;
    @Autowired
    SurveyDao surveyDao;

    @Autowired
    SubmittedSurveyDao submittedSurveyDao;

    @Override
    public ResponseEntity<String> createSurvey(Survey survey) {
        try {
            surveyService.createSurvey(survey);
            return CafeUtils.getResponseEntity("success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Question> addQuestion(int questionId, int surveyId) {
        try {
            return surveyService.addQuestion(questionId, surveyId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> deleteSurvey(int id) {
        try {
            return surveyService.deleteSurvey(id);
        } catch (Exception e) {
            return CafeUtils.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @Override
    public ResponseEntity<List<Survey>> getSurveys() {
        try {
            return surveyService.getSurveys();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Survey>>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<SurveyDTO>> getSurveysDTO() {
        try {
            List<SurveyDTO> surveyDTO = surveyService.getSurveysDTO();
            return new ResponseEntity<>(surveyDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<SurveyDTO>>(null, null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> createQuestion(Question question) {
        try {
            surveyService.createQuestion(question);
            return CafeUtils.getResponseEntity("success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<SurveyWrapper> getSurvey(int id) {
        try {
           SurveyWrapper sW = surveyService.getSurvey(id);
        return new ResponseEntity<SurveyWrapper>(sW, HttpStatus.OK); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<SurveyWrapper>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> submitSurvey(SubmittedSurveyReq survey) {
        try {
                 return surveyService.submitSurvey(survey);         
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<SurveyWrapper> getSubmittedSurvey(int id) {
        try {
        SurveyWrapper sW = surveyService.getSubmittedSurvey(id);
        return new ResponseEntity<SurveyWrapper>(sW, HttpStatus.OK);    
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<SurveyWrapper>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
