package com.projet.v1.module.planner.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
