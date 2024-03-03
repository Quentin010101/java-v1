package com.projet.v1.planner.service;

import com.projet.v1.exception.IncorrectRequestInformation;
import com.projet.v1.planner.dao.Compartiment;
import com.projet.v1.planner.dao.Tag;
import com.projet.v1.planner.repository.CompartimentRepository;
import com.projet.v1.planner.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilsService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CompartimentRepository compartimentRepository;
    @Autowired
    private VerifService verifService;

    public Tag createTag(Tag tag){
        return tagRepository.save(tag);
    }
    public Compartiment createCompartiment(Compartiment compartiment){
        return compartimentRepository.save(compartiment);
    }

    public Tag updateTag(Tag tag) throws IncorrectRequestInformation {
        if(!verifService.tagExist(tag)) throw new IncorrectRequestInformation("This tag doesn't exist");
        return tagRepository.save(tag);
    }
    public Compartiment updateCompartiment(Compartiment compartiment) throws IncorrectRequestInformation {
        if(!verifService.compartimentExist(compartiment)) throw new IncorrectRequestInformation("This compartiment doesn't exist");
        return compartimentRepository.save(compartiment);
    }

    public List<Tag> getAllTags(){
        return tagRepository.findAll();
    }
    public List<Compartiment> getAllCompartiments(){
        return compartimentRepository.findAll();
    }

    public void deleteTag(Integer id) throws IncorrectRequestInformation {
        if(!tagRepository.existsById(id)) throw new IncorrectRequestInformation("This tag doesn't exist");
        tagRepository.deleteById(id);
    }
    public void deleteCompartiment(Integer id) throws IncorrectRequestInformation {
        if(!compartimentRepository.existsById(id)) throw new IncorrectRequestInformation("This compartiment doesn't exist");
        Compartiment comp = compartimentRepository.findById(id).orElseThrow();
        if(verifService.isCompartimentUsed(comp)) throw new IncorrectRequestInformation("This compartiment is used and can't be deleted");
        compartimentRepository.deleteById(id);
    }
}
