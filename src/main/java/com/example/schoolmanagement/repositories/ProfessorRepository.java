package com.example.schoolmanagement.repositories;

import com.example.schoolmanagement.entities.Course;
import com.example.schoolmanagement.entities.Professor;
import com.example.schoolmanagement.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, UUID> {
    long count();

    @Query("select p.courses from Professor p")
    List<Course> getAllCourses();
}
