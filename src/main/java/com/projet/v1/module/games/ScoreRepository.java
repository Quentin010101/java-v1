package com.projet.v1.module.games;

import com.projet.v1.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<ScoreDao, Integer> {

    List<ScoreDao> findAllByUser(User user);
}