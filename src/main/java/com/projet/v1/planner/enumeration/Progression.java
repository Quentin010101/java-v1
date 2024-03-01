package com.projet.v1.planner.enumeration;

public enum Progression {
    DEFAULT("Default"),FINISH("Finish"), PROGRESS("In progress"), START("Not started");

    private String text;
    Progression(String text){
        text = text;
    }
}
