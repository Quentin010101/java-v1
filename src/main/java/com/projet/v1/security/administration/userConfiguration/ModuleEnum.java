package com.projet.v1.security.administration.userConfiguration;

import lombok.Getter;

import java.util.Objects;

public enum ModuleEnum {
    PLANNER(1, "Planner"),GAMES(2,"Games");

    @Getter
    private final Integer moduleId;
    @Getter
    private final String name;

    ModuleEnum(Integer moduleId,String name) {
        this.moduleId = moduleId;
        this.name = name;
    }

    public static ModuleEnum valueOf(Integer id) {
        for (ModuleEnum module : values()) {
            if (Objects.equals(module.moduleId, id)) {
                return module;
            }
        }
        throw new IllegalArgumentException(id.toString());
    }

}
