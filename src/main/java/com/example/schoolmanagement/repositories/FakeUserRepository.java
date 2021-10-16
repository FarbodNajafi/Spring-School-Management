package com.example.schoolmanagement.repositories;

import com.example.schoolmanagement.entities.CustomUserDetails;

import java.util.Optional;

public interface FakeUserRepository {
    Optional<CustomUserDetails> getUserByUsername(String username);
}
