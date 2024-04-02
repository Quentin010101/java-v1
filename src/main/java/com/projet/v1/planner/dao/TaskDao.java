package com.projet.v1.planner.dao;


import com.projet.v1.planner.dao.Commentaire;
import com.projet.v1.planner.dao.Compartiment;
import com.projet.v1.planner.dao.Item;
import com.projet.v1.planner.dao.Tag;
import com.projet.v1.planner.enumeration.Importance;
import com.projet.v1.planner.enumeration.Progression;
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

    @ManyToOne(cascade = {  CascadeType.MERGE })
    private Tag tag;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Item> items;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Commentaire> conmentaires;

    @Override
    public String toString() {
        return "TaskDao{" +
                "taskId=" + taskId +
                ", dateCreation=" + dateCreation +
                ", dateEcheance=" + dateEcheance +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", progression=" + progression +
                ", importance=" + importance +
                ", taskorder=" + taskorder +
                ", compartiment=" + compartiment +
                ", tag=" + tag +
                ", items=" + items +
                ", conmentaires=" + conmentaires +
                '}';
    }
}




