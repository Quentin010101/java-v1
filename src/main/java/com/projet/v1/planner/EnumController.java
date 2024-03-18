package com.projet.v1.planner;

import com.projet.v1.dto.ResponseDto;
import com.projet.v1.dto.ResponseObjectDto;
import com.projet.v1.planner.enumeration.Importance;
import com.projet.v1.planner.enumeration.Progression;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("${frontend.server.url}")
@RequestMapping("/enumeration")
public class EnumController {

    @GetMapping("/importance")
    public ResponseObjectDto<Map<Integer, String>> getPriority(){
        Map<Integer, String> map = new HashMap<>();
        for(Importance imp : Importance.values()){
            map.put(imp.getId(), imp.getText());
        }
        return new ResponseObjectDto<>(new ResponseDto("", true), map);
    }

    @GetMapping("/progression")
    public ResponseObjectDto<Map<Integer, String>> getProgression(){
        Map<Integer, String> map = new HashMap<>();
        for(Progression imp : Progression.values()){
            map.put(imp.getId(), imp.getText());
        }
        return new ResponseObjectDto<>(new ResponseDto("", true), map);
    }
}
