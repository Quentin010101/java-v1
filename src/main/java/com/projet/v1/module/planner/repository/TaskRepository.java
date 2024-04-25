package com.projet.v1.module.planner.repository;

import com.projet.v1.module.planner.dao.Compartiment;
import com.projet.v1.module.planner.dao.TaskDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskDao, Integer> {
    List<TaskDao> findByCompartiment(Compartiment commp);

}

