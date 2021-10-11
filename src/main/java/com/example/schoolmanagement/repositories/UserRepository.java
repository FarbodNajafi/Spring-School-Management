package com.example.schoolmanagement.repositories;

import com.example.schoolmanagement.entities.CustomUserDetails;

import java.util.Optional;

public interface UserRepository {
    Optional<CustomUserDetails> getUserByUsername(String username);
}
