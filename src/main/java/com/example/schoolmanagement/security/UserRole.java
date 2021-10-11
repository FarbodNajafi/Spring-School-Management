package com.example.schoolmanagement.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.schoolmanagement.security.UserPermission.*;

public enum UserRole {
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, COURSE_DELETE, STUDENT_READ, STUDENT_WRITE, STUDENT_DELETE, GRADE_READ, GRADE_WRITE)),
    STAFF(Sets.newHashSet(COURSE_READ, COURSE_WRITE, COURSE_DELETE, STUDENT_READ, GRADE_READ, GRADE_WRITE)),
    PROFESSOR(Sets.newHashSet(COURSE_READ, STUDENT_READ, GRADE_READ, GRADE_WRITE)),
    STUDENT(Sets.newHashSet());

    private final Set<UserPermission> permissions;


    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
