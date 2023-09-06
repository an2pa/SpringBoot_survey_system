package com.inn.cafe.com.inn.cafe.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.inn.cafe.com.inn.cafe.POJO.User;

public interface UserService {
    ResponseEntity<String> signUp(Map<String, String> requestMap);
    ResponseEntity<String> logIn(Map<String, String> requestMap);
    ResponseEntity<List<User>> getUsers();
    ResponseEntity<User> getUser(int id);
    ResponseEntity<String> deleteUser(int id);
    ResponseEntity<String> putUser(int id, Map<String, String> requestMap);
    ResponseEntity<String> changePassword(Map<String, Object> requestMap);
    ResponseEntity<String> forgotPassword(Map<String, Object> requestMap);

}
