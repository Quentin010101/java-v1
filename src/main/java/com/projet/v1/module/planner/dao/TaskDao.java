package com.projet.v1.module.planner.dao;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class TaskDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer taskId;
    private Date dateCreation;
    private Date dateEcheance;
    private String title;
    private String text;
    private Integer progression;
    private Integer importance;
    private Integer taskorder;

    @ManyToOne(cascade = { CascadeType.MERGE })
    private Compartiment compartiment;

    @ManyToMany(cascade = {  CascadeType.MERGE })
    private List<Tag> tags;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Item> items;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Commentaire> commentaires;

//    @Override
//    public String toString() {
//        return "TaskDao{" +
//                "taskId=" + taskId +
//                ", dateCreation=" + dateCreation +
//                ", dateEcheance=" + dateEcheance +
//                ", title='" + title + '\'' +
//                ", text='" + text + '\'' +
//                ", progression=" + progression +
//                ", importance=" + importance +
//                ", taskorder=" + taskorder +
//                ", compartiment=" + compartiment +
//                ", tag=" + tag +
//                ", items=" + items +
//                ", conmentaires=" + conmentaires +
//                '}';
//    }

    @Override
    public String toString() {
        return "TaskDao{" +
                "taskId=" + taskId +
                ", taskorder=" + taskorder +
                ", compartiment id=" +compartiment.getCompartimentId() +
                '}';
    }
}




