package com.example.schoolmanagement.services;


import com.example.schoolmanagement.entities.Course;
import com.example.schoolmanagement.entities.Professor;
import com.example.schoolmanagement.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfessorService {
    private ProfessorRepository professorRepository;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public Professor save(Professor professor) {
        return this.professorRepository.save(professor);
    }

    public List<Professor> getAllProfessors() {
        return this.professorRepository.findAll();
    }

    public Optional<Professor> getById(UUID id) {
        return professorRepository.findById(id);
    }

    public void deleteById(UUID id) {
        professorRepository.deleteById(id);
    }

    public long count() {
        return professorRepository.count();
    }

    //    Todo: fetch professor's students
    public List<Course> getAllCourses() {
        return professorRepository.getAllCourses();
    }
//    Todo: fetch professor's students' average grade
}
