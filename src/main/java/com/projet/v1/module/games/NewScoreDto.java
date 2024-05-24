package com.projet.v1.module.games;

public record NewScoreDto(GameTypeEnum type, Integer score) {
    @Override
    public String toString() {
        return "NewScoreDto{" +
                "type=" + type +
                ", score=" + score +
                '}';
    }
}
