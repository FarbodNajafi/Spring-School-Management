package com.example.schoolmanagement.exceptions;

import java.util.UUID;

public class ProfessorNotFoundException extends RuntimeException {
    public ProfessorNotFoundException(UUID id) {
        super("Could not find professor: " + id);
    }
}
