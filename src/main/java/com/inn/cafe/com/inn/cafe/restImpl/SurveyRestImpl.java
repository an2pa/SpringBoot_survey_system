package com.inn.cafe.com.inn.cafe.restImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafe.com.inn.cafe.POJO.Answer;
import com.inn.cafe.com.inn.cafe.POJO.Question;
import com.inn.cafe.com.inn.cafe.POJO.Survey;
import com.inn.cafe.com.inn.cafe.POJO.SurveyDTO;
import com.inn.cafe.com.inn.cafe.POJO.SurveyWrapper;
import com.inn.cafe.com.inn.cafe.POJO.User;
import com.inn.cafe.com.inn.cafe.dao.AnswerDao;
import com.inn.cafe.com.inn.cafe.dao.QuestionDao;
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
            
            System.out.println(" nesooooooo");
            return surveyService.addQuestion(questionId,surveyId);
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


        
        return surveyService.getSurveys();
    }



    @Override
    public ResponseEntity<List<SurveyDTO>> getSurveysDTO() {
        List<SurveyDTO> surveyDTO = surveyService.getSurveysDTO();
        return new ResponseEntity<>(surveyDTO, HttpStatus.OK);
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
       SurveyWrapper sW = new SurveyWrapper();
       Survey survey = surveyDao.findById(id).orElse(null);
       List<Question> questions = getQuestionsForSurvey(id);
       sW.setName(survey.getName());
       sW.setDescription(survey.getDescription());
       sW.setQuestions(questions);
       //System.out.println("survey"+ sW);
       
       return new ResponseEntity<SurveyWrapper>(sW, HttpStatus.OK);
    }

    public List<Question> getQuestionsForSurvey(int surveyId) {
        // Retrieve the survey by ID
        Survey survey = surveyDao.findById(surveyId).orElse(null);

        if (survey != null) {
            // Get the questions associated with the survey
            return survey.getQuestions();
        }

        return null; // Handle the case where the survey does not exist
    }

    public List<Answer> getAnswersForQuestions(List<Question> questions) {
        // Create a list to store answers
        List<Answer> answers = null;

        if (questions != null && !questions.isEmpty()) {
            // Get answers for the given questions
            answers = answerDao.findByQuestionsIn(questions);
        }

        return answers;
    }



    
}
