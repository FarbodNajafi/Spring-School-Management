package com.example.schoolmanagement.assemblers;

import com.example.schoolmanagement.controllers.ProfessorController;
import com.example.schoolmanagement.entities.Professor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProfessorModelAssembler implements RepresentationModelAssembler<Professor, EntityModel<Professor>> {
    @Override
    public EntityModel<Professor> toModel(Professor entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(ProfessorController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(ProfessorController.class).all()).withRel("professors"));
    }
}
