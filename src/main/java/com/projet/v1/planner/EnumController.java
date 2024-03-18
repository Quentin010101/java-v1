package com.projet.v1.planner;

import com.projet.v1.dto.ResponseDto;
import com.projet.v1.dto.ResponseObjectDto;
import com.projet.v1.planner.dto.Choice;
import com.projet.v1.planner.enumeration.Importance;
import com.projet.v1.planner.enumeration.Progression;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("${frontend.server.url}")
@RequestMapping("/enumeration")
public class EnumController {

    @GetMapping("/importance")
    public ResponseObjectDto<List<Choice>> getPriority(){
        List<Choice> liste = new ArrayList<>();
        for(Importance imp : Importance.values()){
            liste.add(new Choice(imp.getId(), imp.getText()));
        }
        return new ResponseObjectDto<>(new ResponseDto("", true), liste);
    }

    @GetMapping("/progression")
    public ResponseObjectDto<List<Choice>> getProgression(){
        List<Choice> liste = new ArrayList<>();
        for(Progression imp : Progression.values()){
            liste.add(new Choice(imp.getId(), imp.getText()));
        }
        return new ResponseObjectDto<>(new ResponseDto("", true), liste);
    }
}
