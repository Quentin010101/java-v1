package com.projet.v1.planner.service;

import com.projet.v1.planner.dao.Compartiment;
import com.projet.v1.planner.dao.Tag;
import com.projet.v1.planner.dao.TaskDao;
import com.projet.v1.planner.repository.CompartimentRepository;
import com.projet.v1.planner.repository.TagRepository;
import com.projet.v1.planner.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifService {
    @Autowired
    private CompartimentRepository compartimentRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TaskRepository taskRepository;

    public boolean compartimentExist(TaskDao task){
        return compartimentRepository.existsById(task.getCompartiment().getCompartimentId());
    }
    public boolean compartimentExist(Compartiment comp){
        return compartimentRepository.existsById(comp.getCompartimentId());
    }
    public boolean compartimentExist(Integer id){
        return compartimentRepository.existsById(id);
    }
    public boolean tagExist(TaskDao task){
        if(task.getTag() == null) return true;
        return tagRepository.existsById(task.getTag().getTagId());
    }
    public boolean tagExist(Tag tag){
        return tagRepository.existsById(tag.getTagId());
    }

    public boolean isCompartimentUsed(Compartiment comp){
        if(taskRepository.findByCompartiment(comp).isEmpty()) return false;
        return true;
    }
}
