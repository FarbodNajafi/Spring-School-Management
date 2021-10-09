package com.example.schoolmanagement.controllers;

import com.example.schoolmanagement.assemblers.FacultyModelAssembler;
import com.example.schoolmanagement.assemblers.StudentModelAssembler;
import com.example.schoolmanagement.entities.Faculty;
import com.example.schoolmanagement.entities.Student;
import com.example.schoolmanagement.exceptions.FacultyNotFoundException;
import com.example.schoolmanagement.services.FacultyService;
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
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyService facultyService;
    private final StudentService studentService;
    private final FacultyModelAssembler facultyModelAssembler;
    private final StudentModelAssembler studentModelAssembler;

    @Autowired
    public FacultyController(FacultyService facultyService, StudentService studentService, FacultyModelAssembler facultyModelAssembler, StudentModelAssembler studentModelAssembler) {
        this.facultyService = facultyService;
        this.studentService = studentService;
        this.facultyModelAssembler = facultyModelAssembler;
        this.studentModelAssembler = studentModelAssembler;
    }

    @GetMapping("")
    public CollectionModel<EntityModel<Faculty>> all() {
        List<EntityModel<Faculty>> faculties =
                facultyService.getAllFaculties()
                        .stream()
                        .map(facultyModelAssembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(
                faculties,
                linkTo(methodOn(FacultyController.class).all()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public EntityModel<Faculty> one(@PathVariable UUID id) {
        Faculty faculty = facultyService
                .getById(id)
                .orElseThrow(
                        () -> new FacultyNotFoundException(id)
                );

        return facultyModelAssembler.toModel(faculty);
    }

    @GetMapping("/{id}/students")
    public CollectionModel<EntityModel<Student>> allStudentByFaculty(@PathVariable UUID id) {
        Faculty faculty = facultyService
                .getById(id)
                .orElseThrow(
                        () -> new FacultyNotFoundException(id)
                );


        List<EntityModel<Student>> students =
                studentService.getAllStudentsByFaculty(faculty)
                        .stream()
                        .map(studentModelAssembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(
                students,
                linkTo(methodOn(StudentController.class).all()).withSelfRel()
        );
    }

    @PostMapping("/")
    ResponseEntity<?> newFaculty(@RequestBody Faculty newFaculty) {
        EntityModel<Faculty> entityModel =
                facultyModelAssembler.toModel(facultyService.save(newFaculty));

        return ResponseEntity
                .created(entityModel
                        .getRequiredLink(IanaLinkRelations.SELF)
                        .toUri())
                .body(entityModel);
    }


    @PutMapping("/{id}/")
    ResponseEntity<?> replaceFaculty(@RequestBody Faculty newFaculty, @PathVariable UUID id) {
        Faculty updatedFaculty = facultyService.getById(id)
                .map(faculty -> {
                    faculty.setName(newFaculty.getName());
                    faculty.setCourses(newFaculty.getCourses());
                    faculty.setProfessors(newFaculty.getProfessors());
                    faculty.setStudents(newFaculty.getStudents());
                    return facultyService.save(faculty);
                })
                .orElseGet(() -> {
                    newFaculty.setId(id);
                    return facultyService.save(newFaculty);
                });

        EntityModel<Faculty> entityModel =
                facultyModelAssembler.toModel(updatedFaculty);

        return ResponseEntity
                .created(entityModel
                        .getRequiredLink(IanaLinkRelations.SELF)
                        .toUri())
                .body(entityModel);
    }


    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteFaculty(@PathVariable UUID id) {
        facultyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
