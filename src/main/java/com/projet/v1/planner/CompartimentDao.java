package com.projet.v1.planner;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CompartimentDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer compartimentId;
    private String name;
}
