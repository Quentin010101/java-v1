package com.projet.v1.module.planner.repository;

import com.projet.v1.module.planner.dao.Compartiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompartimentRepository extends JpaRepository<Compartiment, Integer> {
}
