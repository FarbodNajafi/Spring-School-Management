package com.example.schoolmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
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
public class Professor {

    private @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(generator = "UUID")
    @Type(type = "org.hibernate.type.UUIDCharType")
    UUID id;

    @Column(nullable = false, unique = true)
    private Long personnelId;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @Column(unique = true)
    private String nationalId;

    @OneToMany(mappedBy = "professor")
    private List<Course> courses;

    @ManyToOne
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    private Faculty faculty;

}
