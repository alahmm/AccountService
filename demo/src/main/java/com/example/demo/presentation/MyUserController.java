package com.example.demo.presentation;

import com.example.demo.businesslayout.MyUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyUserController {

    @PostMapping ("/api/auth/signup")
    public ResponseEntity<String> register(@Valid @RequestBody MyUser myUser) throws JsonProcessingException {
        MyUser myUser1 = new MyUser(myUser.getName(), myUser.getlastname(), myUser.getEmail(), myUser.getPassword());
        ObjectMapper objectMapper = new ObjectMapper();
        return new ResponseEntity<>(objectMapper.writerWithDefaultPrettyPrinter().
                writeValueAsString(myUser1), HttpStatus.OK);
    }
}
