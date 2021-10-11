package com.example.schoolmanagement.repositories;

import com.example.schoolmanagement.entities.User;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.schoolmanagement.security.UserRole.*;

@Repository("fake")
public class FakeUserRepositoryImpl implements UserRepository {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeUserRepositoryImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return getUsers()
                .stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst();
    }

    private List<User> getUsers() {
        List<User> users = Lists.newArrayList(
                new User("admin",
                        passwordEncoder.encode("Testing321"),
                        ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new User("staff",
                        passwordEncoder.encode("Testing321"),
                        STAFF.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new User("prof",
                        passwordEncoder.encode("Testing321"),
                        PROFESSOR.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new User("student",
                        passwordEncoder.encode("Testing321"),
                        STUDENT.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
        );
        return users;
    }
}
