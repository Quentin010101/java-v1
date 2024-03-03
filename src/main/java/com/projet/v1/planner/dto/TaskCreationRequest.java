package com.projet.v1.planner.dto;

import com.projet.v1.planner.dao.Compartiment;

public record TaskCreationRequest(String title, Compartiment compartiment){
}
