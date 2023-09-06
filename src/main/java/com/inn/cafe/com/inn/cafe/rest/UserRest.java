package com.inn.cafe.com.inn.cafe.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inn.cafe.com.inn.cafe.POJO.User;


@RequestMapping(path="/user")

public interface UserRest {
    @PostMapping(path="/signup")
    public ResponseEntity<String> signUp(@RequestBody(required=true) Map<String, String> requestMap);

    @PostMapping(path="/login")
    public ResponseEntity<String> logIn(@RequestBody(required=true) Map<String, String> requestMap);

    @GetMapping(path="/users")
    public ResponseEntity<List<User>> getUsers();

    @GetMapping(path="/userById/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id);
    
    @DeleteMapping(path="/userById/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id);

    @PutMapping(path="/userById/{id}")
    public ResponseEntity<String> putUser(@PathVariable int id, @RequestBody(required=true) Map<String, String> requestMap );

    @PutMapping(path="/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody(required=true) Map<String, Object> requestMap );

     @PutMapping(path="/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody(required=true) Map<String, Object> requestMap );
}
