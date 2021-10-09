package com.example.schoolmanagement.advices;

import com.example.schoolmanagement.exceptions.FacultyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FacultyNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(FacultyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String facultyNotFoundHandler(FacultyNotFoundException ex) {
        return ex.getMessage();
    }
}
