package com.example.schoolmanagement.controllers;

import com.example.schoolmanagement.assemblers.CourseModelAssembler;
import com.example.schoolmanagement.assemblers.ProfessorModelAssembler;
import com.example.schoolmanagement.assemblers.StudentModelAssembler;
import com.example.schoolmanagement.entities.Course;
import com.example.schoolmanagement.entities.Professor;
import com.example.schoolmanagement.entities.Student;
import com.example.schoolmanagement.exceptions.ProfessorNotFoundException;
import com.example.schoolmanagement.services.ProfessorService;
import com.example.schoolmanagement.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/professors")
public class ProfessorController {
    private final ProfessorService professorService;
    private final StudentService studentService;
    private final ProfessorModelAssembler professorModelAssembler;
    private final CourseModelAssembler courseModelAssembler;
    private final StudentModelAssembler studentModelAssembler;

    @Autowired
    public ProfessorController(ProfessorService professorService, StudentService studentService, ProfessorModelAssembler professorModelAssembler, CourseModelAssembler courseModelAssembler, StudentModelAssembler studentModelAssembler) {
        this.professorService = professorService;
        this.studentService = studentService;
        this.professorModelAssembler = professorModelAssembler;
        this.courseModelAssembler = courseModelAssembler;
        this.studentModelAssembler = studentModelAssembler;
    }

    @GetMapping("")
    public CollectionModel<EntityModel<Professor>> all() {
        List<EntityModel<Professor>> professors =
                professorService.getAllProfessors()
                        .stream()
                        .map(professorModelAssembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(
                professors,
                linkTo(methodOn(ProfessorController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Professor> one(@PathVariable UUID id) {
        Professor professor = professorService
                .getById(id)
                .orElseThrow(
                        () -> new ProfessorNotFoundException(id)
                );

        return professorModelAssembler.toModel(professor);
    }

    @GetMapping("/{id}/courses")
    public CollectionModel<EntityModel<Course>> allCourses(@PathVariable UUID id) {
        Professor professor =
                professorService.getById(id)
                        .orElseThrow(
                                () -> new ProfessorNotFoundException(id)
                        );
        List<Course> courses = professor.getCourses();

        List<EntityModel<Course>> entityModel = courses
                .stream()
                .map(courseModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                entityModel,
                linkTo(methodOn(CourseController.class).all()).withSelfRel()
        );

    }

    @GetMapping("/{id}/students")
    public CollectionModel<EntityModel<Student>> allStudents(@PathVariable UUID id) {
        Professor professor =
                professorService.getById(id)
                        .orElseThrow(
                                () -> new ProfessorNotFoundException(id)
                        );
        List<Course> courses = professor.getCourses();

        List<Student> students = studentService.getAllStudentsByCourses(courses);

        List<EntityModel<Student>> entityModel = students
                .stream()
                .map(studentModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                entityModel,
                linkTo(methodOn(StudentController.class).all()).withSelfRel()
        );
    }

    @PostMapping("/")
    ResponseEntity<?> newProfessor(@RequestBody Professor newProfessor) {
        long count = professorService.count() + 1;
        newProfessor.setPersonnelId(count);

        EntityModel<Professor> entityModel =
                professorModelAssembler.toModel(professorService.save(newProfessor));

        return ResponseEntity
                .created(entityModel
                        .getRequiredLink(IanaLinkRelations.SELF)
                        .toUri())
                .body(entityModel);
    }


    @PutMapping("/{id}/")
    ResponseEntity<?> replaceProfessor(@RequestBody Professor newProfessor, @PathVariable UUID id) {
        long count = professorService.count() + 1;
        Professor updatedProfessor = professorService.getById(id)
                .map(professor -> {
                    professor.setFirstName(newProfessor.getFirstName());
                    professor.setLastName(newProfessor.getLastName());
                    professor.setNationalId(newProfessor.getNationalId());
                    professor.setPersonnelId(newProfessor.getPersonnelId());
                    professor.setFaculty(newProfessor.getFaculty());
                    professor.setCourses(newProfessor.getCourses());
                    professor.setPersonnelId(count);
                    return professorService.save(professor);
                })
                .orElseGet(() -> {
                    newProfessor.setId(id);
                    newProfessor.setPersonnelId(count);
                    return professorService.save(newProfessor);
                });

        EntityModel<Professor> entityModel =
                professorModelAssembler.toModel(updatedProfessor);

        return ResponseEntity
                .created(entityModel
                        .getRequiredLink(IanaLinkRelations.SELF)
                        .toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteProfessor(@PathVariable UUID id) {
        professorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
