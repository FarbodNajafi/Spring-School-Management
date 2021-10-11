package com.example.schoolmanagement.repositories;

import com.example.schoolmanagement.entities.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository {
    Optional<User> getUserByUsername(String username);
}
