package com.projet.v1.planner;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class TaskDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer taskId;
    private Date dateCreation;
    private Date dateEcheance;
    private CompartimentDao compartiment;
    private Progression progression;
}
