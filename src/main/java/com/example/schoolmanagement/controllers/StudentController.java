package com.example.schoolmanagement.controllers;

import com.example.schoolmanagement.assemblers.StudentModelAssembler;
import com.example.schoolmanagement.entities.Course;
import com.example.schoolmanagement.entities.Student;
import com.example.schoolmanagement.exceptions.StudentNotFoundException;
import com.example.schoolmanagement.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final StudentModelAssembler assembler;

    @Autowired
    public StudentController(StudentService studentService, StudentModelAssembler assembler) {
        this.studentService = studentService;
        this.assembler = assembler;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('student:read')")
    public CollectionModel<EntityModel<Student>> all() {
        List<EntityModel<Student>> students =
                studentService.getAllStudents()
                        .stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(
                students,
                linkTo(methodOn(StudentController.class).all()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public EntityModel<Student> one(@PathVariable UUID id) {
        Student student = studentService
                .getById(id)
                .orElseThrow(
                        () -> new StudentNotFoundException(id)
                );

        return assembler.toModel(student);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('student:write') or hasAnyRole('ROLE_ADMIN')")
    ResponseEntity<?> newStudent(@RequestBody Student newStudent) {
        long count = studentService.count() + 1;
        newStudent.setStudentId(count);
        EntityModel<Student> entityModel =
                assembler.toModel(studentService.save(newStudent));

        return ResponseEntity
                .created(entityModel
                        .getRequiredLink(IanaLinkRelations.SELF)
                        .toUri())
                .body(entityModel);
    }


    @PutMapping("/{id}/")
    @PreAuthorize("hasAuthority('student:write') or hasAnyRole('ROLE_ADMIN')")
    ResponseEntity<?> replaceStudent(@RequestBody Student newStudent, @PathVariable UUID id) {
        long count = studentService.count() + 1;
        Student updatedStudent = studentService.getById(id)
                .map(student -> {
                    student.setFirstName(newStudent.getFirstName());
                    student.setLastName(newStudent.getLastName());
                    student.setNationalId(newStudent.getNationalId());
                    student.setFaculty(newStudent.getFaculty());
                    student.setCourses(newStudent.getCourses());
                    return studentService.save(student);
                })
                .orElseGet(() -> {
                    newStudent.setId(id);
                    newStudent.setStudentId(count);
                    return studentService.save(newStudent);
                });

        EntityModel<Student> entityModel =
                assembler.toModel(updatedStudent);

        return ResponseEntity
                .created(entityModel
                        .getRequiredLink(IanaLinkRelations.SELF)
                        .toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('student:delete') or hasRole('ROLE_ADMIN')")
    ResponseEntity<?> deleteStudent(@PathVariable UUID id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/courses/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    ResponseEntity<?> addCourse(@RequestBody Course newCourse, @PathVariable UUID id) {

        Student student = studentService
                .getById(id)
                .orElseThrow(
                        () -> new StudentNotFoundException(id)
                );

        List<Course> courses = student.getCourses();
        courses.add(newCourse);
        student.setCourses(courses);

        EntityModel<Student> entityModel = assembler.toModel(student);


        return ResponseEntity
                .created(entityModel
                        .getRequiredLink(IanaLinkRelations.SELF)
                        .toUri())
                .body(entityModel);
    }
}
