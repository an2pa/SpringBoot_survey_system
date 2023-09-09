package com.inn.cafe.com.inn.cafe.POJO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class SubmittedSurveyReq {
    private Integer surveyId;
    private List<QuestionAnswer> questionAnswer;
}
