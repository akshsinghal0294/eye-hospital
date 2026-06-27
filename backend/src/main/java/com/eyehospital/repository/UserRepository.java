package com.eyehospital.repository;

import com.eyehospital.entity.AppUser;
import com.eyehospital.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    boolean existsByUsername(String username);

    List<AppUser> findByRole(Role role);

    List<AppUser> findByActiveTrue();
}