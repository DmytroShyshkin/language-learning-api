package com.dmytro.language_learning_api.repository;

import com.dmytro.language_learning_api.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByUsername(String username);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
