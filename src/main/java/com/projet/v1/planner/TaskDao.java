package com.projet.v1.planner;


import com.projet.v1.planner.dao.Commentaire;
import com.projet.v1.planner.dao.Compartiment;
import com.projet.v1.planner.dao.Item;
import com.projet.v1.planner.dao.Tag;
import com.projet.v1.planner.enumeration.Importance;
import com.projet.v1.planner.enumeration.Progression;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class TaskDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer taskId;
    private Date dateCreation;
    private Date dateEcheance;
    private String title;
    private String text;
    private Progression progression;
    private Importance importance;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Compartiment compartiment;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Tag tag;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Item> items;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Commentaire> conmentaires;
}
