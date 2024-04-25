package com.projet.v1.module.planner.enumeration;

import lombok.Getter;

@Getter
public enum Importance {
    DEFAULT(1,"Default"),
    URGENT(2, "Urgent"),
    IMPORTANT(3, "Important"),
    MOYEN(4, "Moyen"),
    MINIMUM(5, "Minimum");

    private final Integer id;
    private final String text;

    Importance(Integer id, String text){
        this.id=id;
        this.text=text;
    }
}
