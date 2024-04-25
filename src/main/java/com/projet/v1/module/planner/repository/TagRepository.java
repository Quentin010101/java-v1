package com.projet.v1.module.planner.repository;

import com.projet.v1.module.planner.dao.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
