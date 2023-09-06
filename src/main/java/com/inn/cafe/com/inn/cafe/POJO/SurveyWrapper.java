package com.inn.cafe.com.inn.cafe.POJO;

import java.util.List;

import lombok.Data;

@Data
public class SurveyWrapper {
    private String name;
    private String description;
    private List<Question> questions;
    private List<Answer> answers;

    // Constructors, getters, and setters

    public SurveyWrapper() {
        // Default constructor
    }

}
