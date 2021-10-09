package com.example.schoolmanagement.services;


import com.example.schoolmanagement.entities.Faculty;
import com.example.schoolmanagement.repositories.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FacultyService {
    private FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty save(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Optional<Faculty> getById(UUID id) {
        return facultyRepository.findById(id);
    }

    public void deleteById(UUID id) {
        facultyRepository.deleteById(id);
    }

}
