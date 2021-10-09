package com.example.schoolmanagement.repositories;

import com.example.schoolmanagement.entities.Course;
import com.example.schoolmanagement.entities.Faculty;
import com.example.schoolmanagement.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    @Query("select c.students from Course c where c.id = ?1")
    List<Student> getStudentsByCourseId(UUID id);

    List<Student> findAllByCoursesIn(Iterable<Course> courses);

    List<Student> getStudentsByFaculty(Faculty faculty);
}
