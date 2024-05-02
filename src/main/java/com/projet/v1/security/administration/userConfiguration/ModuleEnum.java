package com.projet.v1.security.administration.userConfiguration;

import lombok.Getter;

public enum ModuleEnum {
    PLANNER(1, "Planner");

    @Getter
    private final Integer moduleId;
    @Getter
    private final String name;

    ModuleEnum(Integer moduleId,String name) {
        this.moduleId = moduleId;
        this.name = name;
    }


}
