package com.inn.cafe.com.inn.cafe.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inn.cafe.com.inn.cafe.POJO.Question;
import com.inn.cafe.com.inn.cafe.POJO.SubmittedSurvey;
import com.inn.cafe.com.inn.cafe.POJO.SubmittedSurveyReq;
import com.inn.cafe.com.inn.cafe.POJO.Survey;
import com.inn.cafe.com.inn.cafe.POJO.SurveyDTO;
import com.inn.cafe.com.inn.cafe.POJO.SurveyWrapper;
import com.inn.cafe.com.inn.cafe.POJO.User;


@RequestMapping(path="/survey")

public interface SurveyRest {
    @PostMapping(path="/createSurvey")
    public ResponseEntity<String> createSurvey(@RequestBody(required=true) Survey survey);

    @PostMapping(path="/submitSurvey")
    public ResponseEntity<String> submitSurvey(@RequestBody(required=true) SubmittedSurveyReq survey);

    @PostMapping(path="/createQuestion")
    public ResponseEntity<String> createQuestion(@RequestBody(required=true) Question question);

    @GetMapping(path="/surveys")
    public ResponseEntity<List<Survey>> getSurveys();

    @GetMapping(path="/surveyByID/{id}")
    public ResponseEntity<SurveyWrapper> getSurvey(@PathVariable int id);

    @GetMapping(path="/submittedSurveyByID/{id}")
    public ResponseEntity<SurveyWrapper> getSubmittedSurvey(@PathVariable int id);

    @GetMapping(path="/surveysDTO")
    public ResponseEntity<List<SurveyDTO>> getSurveysDTO();

    @PostMapping(path="/addQuestionById/{questionId}/{surveyId}")
    public ResponseEntity<Question> addQuestion(@PathVariable int questionId, @PathVariable int surveyId);
    
    @DeleteMapping(path="/surveyById/{id}")
    public ResponseEntity<String> deleteSurvey(@PathVariable int id);



    /*
    @PutMapping(path="/userById/{id}")
    public ResponseEntity<String> putUser(@PathVariable int id, @RequestBody(required=true) Map<String, String> requestMap );
    */
}
