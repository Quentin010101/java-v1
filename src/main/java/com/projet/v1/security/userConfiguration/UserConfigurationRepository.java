package com.projet.v1.security.userConfiguration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserConfigurationRepository extends JpaRepository<UserConfigurationDao, Integer> {
    Optional<UserConfigurationDao> findByUserId(Integer userId);
}
