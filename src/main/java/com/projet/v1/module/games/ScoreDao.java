package com.projet.v1.module.games;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projet.v1.module.planner.dao.Compartiment;
import com.projet.v1.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer scoreId;
    private Integer highScore;
    private GameTypeEnum gameType;

    @JsonIgnore
    @ManyToOne(cascade = { CascadeType.MERGE })
    private User user;

}
