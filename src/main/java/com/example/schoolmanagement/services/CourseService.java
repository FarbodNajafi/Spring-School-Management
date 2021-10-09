package com.example.schoolmanagement.services;

import com.example.schoolmanagement.entities.Course;
import com.example.schoolmanagement.entities.Student;
import com.example.schoolmanagement.repositories.CourseRepository;
import com.example.schoolmanagement.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getById(UUID id) {
        return courseRepository.findById(id);
    }

    public void deleteById(UUID id) {
        courseRepository.deleteById(id);
    }
}
