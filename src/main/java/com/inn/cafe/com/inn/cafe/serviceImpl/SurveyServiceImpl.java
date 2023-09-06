package com.inn.cafe.com.inn.cafe.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.cafe.com.inn.cafe.POJO.Answer;
import com.inn.cafe.com.inn.cafe.POJO.Question;
import com.inn.cafe.com.inn.cafe.POJO.Survey;
import com.inn.cafe.com.inn.cafe.POJO.SurveyDTO;
import com.inn.cafe.com.inn.cafe.POJO.User;
import com.inn.cafe.com.inn.cafe.dao.AnswerDao;
import com.inn.cafe.com.inn.cafe.dao.QuestionDao;
import com.inn.cafe.com.inn.cafe.dao.SurveyDao;
import com.inn.cafe.com.inn.cafe.dao.UserDao;
import com.inn.cafe.com.inn.cafe.service.SurveyService;
import com.inn.cafe.com.inn.cafe.utils.CafeUtils;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Service
public class SurveyServiceImpl implements SurveyService {
    @PersistenceContext
private EntityManager entityManager;

    @Autowired
    SurveyDao surveyDao;
    @Autowired
    QuestionDao questionDao;

    @Autowired
    AnswerDao answerDao;

    

    @Override
    public Survey createSurvey(Survey survey) {

        System.out.println("survey    " + survey);
        return surveyDao.save(survey);

    }

    @Override
    public ResponseEntity<Question> addQuestion(int questionId, int surveyId) {
        try {
            int _id = questionId;
            if (_id != 0) {
                Optional<Question> optQ= questionDao.findById(_id);
                System.out.println("dao: "+optQ);

                Optional<Survey> optS= surveyDao.findById(surveyId);
                System.out.println("sdao: " +optS);

                 // Set the ID parameter
                Question question = optQ.get(); // Execute the query and retrieve the result
                
                Survey survey=optS.get();

                List<Question> questions= survey.getQuestions();

                questions.add(question);

                survey.setQuestions(questions);

                System.out.println("Survey new"+survey);
                surveyDao.save(survey);
                

                if (!Objects.isNull(question)) {

                    System.out.println("Found Question: " + question);

                    return new ResponseEntity<>(question, HttpStatus.OK);
                } else {

                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } else {
                // Handle the case where 'id' is missing or not an integer
                System.out.println("Invalid 'id' value in requestMap: " + questionId);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (DataAccessException e) {
            // Log detailed error message
            System.err.println("Error while fetching Question from the database: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> deleteSurvey(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteSurvey'");
    }

    @Override
    public ResponseEntity<List<Survey>> getSurveys() {
        List<Question> quest=getQuestionsForSurvey(2);
        List<Answer> ans=getAnswersForQuestions(quest);
        for (Question que : quest){
            System.out.println("Answers for ths"+que.getAnswers());
        }
        System.out.println(quest);
        List<Survey> surveys = surveyDao.findAll();
        return new ResponseEntity<>(surveys, HttpStatus.OK);
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








    @Override
    public List<SurveyDTO> getSurveysDTO() {
        List<Survey> surveys = surveyDao.findAll();
        return surveys.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private SurveyDTO convertToDTO(Survey survey) {
        SurveyDTO dto = new SurveyDTO();
        dto.setId(survey.getId());
        dto.setName(survey.getName());
        dto.setDescription(survey.getDescription());
        dto.setQuestions(survey.getQuestions()); 
        return dto;
    }

    @Override
    public Question createQuestion(Question question) {
        System.out.println("quest    " + question);
        return questionDao.save(question);
    }

    @Override
    public SurveyDTO getSurvey(int id) {
        Survey survey = surveyDao.findById(id).orElse(null);
        SurveyDTO sDto=new SurveyDTO();
        sDto.setId(id);
        sDto.setName(survey.getName());
        sDto.setDescription(null);
        List<Question> quest=getQuestionsForSurvey(id);
        sDto.setQuestions(quest);
        List<Answer> ans=getAnswersForQuestions(quest);
        sDto.setAnswers(ans);

        System.out.println("SUrveey");
        System.out.println(sDto);
        return sDto;
    }

}
