package com.example.schoolmanagement.exceptions;

import java.util.UUID;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(UUID id) {
        super("Could not find student: " + id);
    }
}
