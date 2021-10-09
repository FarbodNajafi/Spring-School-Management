package com.example.schoolmanagement.controllers;

import com.example.schoolmanagement.assemblers.CourseModelAssembler;
import com.example.schoolmanagement.entities.Course;
import com.example.schoolmanagement.exceptions.CourseNotFoundException;
import com.example.schoolmanagement.services.CourseService;
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
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final CourseModelAssembler assembler;

    @Autowired
    public CourseController(CourseService courseService, CourseModelAssembler assembler) {
        this.courseService = courseService;
        this.assembler = assembler;
    }

//    @GetMapping("")
//    List<Course> all() {
//        return courseService.getAllCourses();
//    }

    @GetMapping("")
    public CollectionModel<EntityModel<Course>> all() {
        List<EntityModel<Course>> courses =
                courseService.getAllCourses()
                        .stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(
                courses,
                linkTo(methodOn(CourseController.class).all()).withSelfRel()
        );
    }
//
//    @GetMapping("/{id}")
//    Course one(@PathVariable Long id) {
//        return courseService
//                .getById(id)
//                .orElseThrow(
//                        () -> new CourseNotFoundException(id)
//                );
//    }

    @GetMapping("/{id}")
    public EntityModel<Course> one(@PathVariable UUID id) {
        Course course = courseService
                .getById(id)
                .orElseThrow(
                        () -> new CourseNotFoundException(id)
                );

        return assembler.toModel(course);
    }
//
//    @PostMapping("/")
//    Course newCourse(@RequestBody Course course) {
//        return courseService.save(course);
//    }

    @PostMapping("/")
    ResponseEntity<?> newCourse(@RequestBody Course newCourse) {
        EntityModel<Course> entityModel =
                assembler.toModel(courseService.save(newCourse));

        return ResponseEntity
                .created(entityModel
                        .getRequiredLink(IanaLinkRelations.SELF)
                        .toUri())
                .body(entityModel);
    }

//    @PutMapping("/{id}/")
//    Course replaceCourse(@RequestBody Course newCourse, @PathVariable Long id) {
//        return courseService.getById(id)
//                .map(course -> {
//                    course.setName(newCourse.getName());
//                    course.setUnit(newCourse.getUnit());
//                    course.setProfessor(newCourse.getProfessor());
//                    return courseService.save(course);
//                })
//                .orElseGet(() -> {
//                    newCourse.setId(id);
//                    return courseService.save(newCourse);
//                });
//    }

    @PutMapping("/{id}/")
    ResponseEntity<?> replaceCourse(@RequestBody Course newCourse, @PathVariable UUID id) {
        Course updatedCourse =
                courseService.getById(id)
                        .map(course -> {
                            course.setName(newCourse.getName());
                            course.setUnit(newCourse.getUnit());
                            course.setProfessor(newCourse.getProfessor());
                            course.setFaculty(newCourse.getFaculty());
                            course.setStudents(newCourse.getStudents());
                            return courseService.save(course);
                        })
                        .orElseGet(() -> {
                            newCourse.setId(id);
                            return courseService.save(newCourse);
                        });

        EntityModel<Course> entityModel =
                assembler.toModel(updatedCourse);

        return ResponseEntity
                .created(entityModel
                        .getRequiredLink(IanaLinkRelations.SELF)
                        .toUri())
                .body(entityModel);
    }

//    @DeleteMapping("/{id}")
//    void deleteCourse(@PathVariable Long id) {
//        courseService.deleteById(id);
//    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCourse(@PathVariable UUID id) {
        courseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
