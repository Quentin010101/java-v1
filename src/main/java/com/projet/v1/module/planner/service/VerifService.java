package com.projet.v1.module.planner.service;

import com.projet.v1.module.planner.dao.Compartiment;
import com.projet.v1.module.planner.repository.CompartimentRepository;
import com.projet.v1.module.planner.dao.Tag;
import com.projet.v1.module.planner.dao.TaskDao;
import com.projet.v1.module.planner.repository.TagRepository;
import com.projet.v1.module.planner.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public boolean tagsExist(TaskDao task){
        if(task.getTags() == null) return true;
        List<Tag> list = task.getTags();
        boolean result = true;
        for (Tag tag : list) {
            if (!tagRepository.existsById(tag.getTagId())) {
                result = false;
                break;
            }
        }
        return result;
    }
    public boolean tagExist(Tag tag){
        return tagRepository.existsById(tag.getTagId());
    }

    public boolean isCompartimentUsed(Compartiment comp){
        if(taskRepository.findByCompartiment(comp).isEmpty()) return false;
        return true;
    }
}
