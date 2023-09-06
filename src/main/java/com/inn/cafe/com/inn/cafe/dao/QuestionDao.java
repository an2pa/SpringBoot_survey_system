package com.inn.cafe.com.inn.cafe.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inn.cafe.com.inn.cafe.POJO.Question;

public interface QuestionDao extends JpaRepository<Question, Integer> {
    
}
