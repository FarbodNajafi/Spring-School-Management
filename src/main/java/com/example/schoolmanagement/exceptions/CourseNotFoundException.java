package com.example.schoolmanagement.exceptions;

import java.util.UUID;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(UUID id) {
        super("Could not find course: " + id);
    }
}
