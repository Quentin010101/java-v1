package com.projet.v1.module.planner.dao;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compartiment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer compartimentId;
    private String name;
    private Integer compartimentOrder;
}
