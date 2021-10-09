package com.example.schoolmanagement.services;

import com.example.schoolmanagement.entities.Course;
import com.example.schoolmanagement.entities.Faculty;
import com.example.schoolmanagement.entities.Student;
import com.example.schoolmanagement.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getById(UUID id) {
        return studentRepository.findById(id);
    }

    public void deleteById(UUID id) {
        studentRepository.deleteById(id);
    }

    //    Todo: fetch student's grades

    public long count() {
        return studentRepository.count();
    }

    public List<Student> getAllStudentsByCourseId(UUID id) {
        return studentRepository.getStudentsByCourseId(id);
    }

    public List<Student> getAllStudentsByCourses(List<Course> courses) {
        return studentRepository.findAllByCoursesIn(courses);
    }

    public List<Student> getAllStudentsByFaculty(Faculty faculty) {
        return studentRepository.getStudentsByFaculty(faculty);
    }
}
