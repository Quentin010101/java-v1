package com.projet.v1.module.planner.dto;

import com.projet.v1.module.planner.dao.Compartiment;

public record TaskCreationRequest(String title, Compartiment compartiment){
}
