package com.inn.cafe.com.inn.cafe.POJO;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SurveyDTO {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("questions")
    private List<Question> questions;
    @JsonProperty("answers")
    private List<Answer> answers;

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }

    public List<Question> getQuestions(){
        return this.questions;
    }
    public List<Answer> getAnswers(){
        return this.answers;
    }
    
    public void setId(int id){
         this.id=id;
    }

    public void setName(String name){
         this.name=name;
    }
    public void setDescription(String description){
         this.description=description;
    }

    public void setQuestions(List<Question> questions){
         this.questions=questions;
    }
    public void setAnswers(List<Answer> answers){
         this.answers=answers;
    }

    @Override
    public String toString() {
        return "SurveyDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", questions=" + questions +
                ", answers=" + answers +
                '}';
    }



}
