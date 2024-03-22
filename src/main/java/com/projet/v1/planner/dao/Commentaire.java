package com.projet.v1.planner.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer commentaireId;
    private String text;
    private Date dateCreation;

    @Override
    public String toString() {
        return "Commentaire{" +
                "commentaireId=" + commentaireId +
                ", text='" + text + '\'' +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
