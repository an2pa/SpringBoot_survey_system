package com.inn.cafe.com.inn.cafe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.inn.cafe.com.inn.cafe.POJO.Survey;
import com.inn.cafe.com.inn.cafe.POJO.User;

public interface SurveyDao extends JpaRepository<Survey, Integer> {
    List<Survey> findByDeletedFalse();
}
