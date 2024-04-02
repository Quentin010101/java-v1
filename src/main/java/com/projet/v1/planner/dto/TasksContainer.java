package com.projet.v1.planner.dto;

import com.projet.v1.planner.dao.TaskDao;

import java.util.List;

public record TasksContainer(Integer compartimentId, List<TaskDao> tasks) {
}
