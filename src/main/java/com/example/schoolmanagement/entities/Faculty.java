package com.example.schoolmanagement.entities;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

}
