package com.example.schoolmanagement.assemblers;

import com.example.schoolmanagement.controllers.FacultyController;
import com.example.schoolmanagement.entities.Faculty;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FacultyModelAssembler implements RepresentationModelAssembler<Faculty, EntityModel<Faculty>> {
    @Override
    public EntityModel<Faculty> toModel(Faculty entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(FacultyController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(FacultyController.class).all()).withRel("faculties")
        );
    }
}
