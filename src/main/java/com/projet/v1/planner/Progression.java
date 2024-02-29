package com.projet.v1.planner;

public enum Progression {
    FINISH("finish"), PROGRESS("In progress"), START("Not started");

    private String text;
    Progression(String text){
        text = text;
    }
}
