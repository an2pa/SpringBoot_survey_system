package com.inn.cafe.com.inn.cafe.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

import com.inn.cafe.com.inn.cafe.JWT.JwtFilter;
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

    @Autowired
    SubmittedSurveyDao submittedSurveyDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> createSurvey(Survey survey) {
        try {
            if (jwtFilter.isAdmin()) {
                surveyDao.save(survey);
                return CafeUtils.getResponseEntity("Success", HttpStatus.OK);
            } else {
                return CafeUtils.getResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Question> addQuestion(int questionId, int surveyId) {
        try {
            if (jwtFilter.isAdmin()) {
                int _id = questionId;
                if (_id != 0) {
                    Optional<Question> optQ = questionDao.findById(_id);
                    Optional<Survey> optS = surveyDao.findById(surveyId);
                    Question question = optQ.get();
                    Survey survey = optS.get();
                    List<Question> questions = survey.getQuestions();
                    questions.add(question);
                    survey.setQuestions(questions);
                    surveyDao.save(survey);

                    if (!Objects.isNull(question)) {
                        return new ResponseEntity<>(question, HttpStatus.OK);
                    } else {

                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                } else {
                    System.out.println("Invalid 'id' value in requestMap: " + questionId);
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (DataAccessException e) {
            System.err.println("Error while fetching Question from the database: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> deleteSurvey(int id) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<Survey> surveyOptional = surveyDao.findById(id);
                if (surveyOptional.isPresent()) {
                    Survey survey=surveyOptional.get();
                    survey.setDeleted(true);
                    surveyDao.save(survey);
                    return CafeUtils.getResponseEntity("Successfully deleted survey with id: " + id, HttpStatus.OK);
                } else {
                    return CafeUtils.getResponseEntity("User with this id does not exist", HttpStatus.NOT_FOUND);
                }
            } else {
                return CafeUtils.getResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CafeUtils.getResponseEntity("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Survey>> getSurveys() {
        try {
            List<Survey> surveys = surveyDao.findByDeletedFalse();
            return new ResponseEntity<>(surveys, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Survey>>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<List<SurveyDTO>> getSurveysDTO() {
        try {
            List<Survey> surveys = surveyDao.findAll();
            List<SurveyDTO> surveyDTOs = surveys.stream().map(this::convertToDTO).collect(Collectors.toList());
            return new ResponseEntity<List<SurveyDTO>>(surveyDTOs, null, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<SurveyDTO>>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

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
    public ResponseEntity<String> createQuestion(Question question) {
        try {
            if (jwtFilter.isAdmin()) {
                questionDao.save(question);
                return CafeUtils.getResponseEntity("Successfully added a question", HttpStatus.OK);
            } else {
                return CafeUtils.getResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<SurveyWrapper> getSurvey(int id) {
        try {
            SurveyWrapper sW = new SurveyWrapper();
            Survey survey = surveyDao.findById(id).orElse(null);
            List<Question> questions = getQuestionsForSurvey(id);
            sW.setName(survey.getName());
            sW.setDescription(survey.getDescription());
            sW.setQuestions(questions);
            return new ResponseEntity<SurveyWrapper>(sW, null, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<SurveyWrapper>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> submitSurvey(SubmittedSurveyReq survey) {
        try {
            SubmittedSurvey submittedSurvey = new SubmittedSurvey();
            Integer surveyId = survey.getSurveyId();
            Survey surveyOptional = surveyDao.findById(surveyId).orElse(null);

            submittedSurvey.setName(surveyOptional.getName());
            submittedSurvey.setDescription(surveyOptional.getDescription());

            List<Question> questions = new ArrayList<>();

            for (QuestionAnswer qa : survey.getQuestionAnswer()) {
                Optional<Question> questionOptional = questionDao.findById(qa.getQuestionId());

                if (questionOptional.isPresent()) {
                    Question question = questionOptional.get();
                    List<Answer> answers = new ArrayList<>();

                    Integer answerId = qa.getAnswerId();
                    Optional<Answer> answerOptional = answerDao.findById(answerId);

                    if (answerOptional.isPresent()) {
                        Answer answer = answerOptional.get();
                        answers.add(answer);
                    } else {
                        return CafeUtils.getResponseEntity("Answers with provided ID do not exist",
                                HttpStatus.BAD_REQUEST);
                    }

                    question.setAnswers(answers);
                    questions.add(question);
                } else {
                    return CafeUtils.getResponseEntity("Questions with provided ID do not exist",
                            HttpStatus.BAD_REQUEST);
                }
            }

            submittedSurvey.setQuestions(questions);
            submittedSurveyDao.save(submittedSurvey);
            return CafeUtils.getResponseEntity("Successfully submitted a survey", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return CafeUtils.getResponseEntity("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<SurveyWrapper> getSubmittedSurvey(int id) {
        try {
            if (jwtFilter.isAdmin()) {
                SurveyWrapper sW = new SurveyWrapper();
                SubmittedSurvey survey = submittedSurveyDao.findById(id).orElse(null);
                sW.setName(survey.getName());
                sW.setDescription(survey.getDescription());
                sW.setQuestions(survey.getQuestions());
                return new ResponseEntity<SurveyWrapper>(sW, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<SurveyWrapper>(null, null, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<SurveyWrapper>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
