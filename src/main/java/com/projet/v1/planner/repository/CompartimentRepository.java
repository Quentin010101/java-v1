package com.projet.v1.planner.repository;

import com.projet.v1.planner.dao.Compartiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompartimentRepository extends JpaRepository<Compartiment, Integer> {
}
