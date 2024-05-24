package com.projet.v1.module.games;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum GameTypeEnum {
    SNAKE(1), MASTERMIND(2);

    private final Integer id;

    GameTypeEnum(Integer id){
        this.id = id;
    }
}
