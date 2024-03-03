package com.projet.v1.planner.dao;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Compartiment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer compartimentId;
    private String name;
    private Integer compartimentOrder;
}
