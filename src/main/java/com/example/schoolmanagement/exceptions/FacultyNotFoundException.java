package com.example.schoolmanagement.exceptions;

import java.util.UUID;

public class FacultyNotFoundException extends RuntimeException {
    public FacultyNotFoundException(UUID id) {
        super("Could not find faculty: " + id);
    }
}
