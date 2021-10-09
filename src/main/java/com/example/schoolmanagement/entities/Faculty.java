package com.example.schoolmanagement.entities;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Faculty {
    private @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(generator = "UUID")
    @Type(type = "org.hibernate.type.UUIDCharType")
    UUID id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "faculty")
    private List<Student> students;

    @OneToMany(mappedBy = "faculty")
    private List<Professor> professors;

    @OneToMany(mappedBy = "faculty")
    private List<Course> courses;


    public Faculty() {
    }

    public Faculty(UUID id, String name, List<Student> students, List<Professor> professors, List<Course> courses) {
        this.id = id;
        this.name = name;
        this.students = students;
        this.professors = professors;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public UUID getId() {
        return id;

    }

    public void setId(UUID id) {
        this.id = id;
    }
}
