package com.example.schoolmanagement.assemblers;

import com.example.schoolmanagement.controllers.StudentController;
import com.example.schoolmanagement.entities.Student;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StudentModelAssembler implements RepresentationModelAssembler<Student, EntityModel<Student>> {
    @Override
    public EntityModel<Student> toModel(Student entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(StudentController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(StudentController.class).all()).withRel("students")
        );
    }
}
