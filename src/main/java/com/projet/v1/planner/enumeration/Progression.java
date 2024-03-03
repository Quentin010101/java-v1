package com.projet.v1.planner.enumeration;

import lombok.Getter;

@Getter
public enum Progression {
    DEFAULT(1,"Default"),
    FINISH(2,"Finish"),
    PROGRESS(3,"In progress"),
    START(4,"Not started");

    private final String text;
    private final Integer id;
    Progression(Integer id, String text){
        this.text = text;
        this.id = id;
    }
}
