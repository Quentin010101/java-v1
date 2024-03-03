package com.projet.v1.planner;

import com.projet.v1.dto.ResponseDto;
import com.projet.v1.dto.ResponseObjectDto;
import com.projet.v1.exception.IncorrectRequestInformation;
import com.projet.v1.planner.dao.Compartiment;
import com.projet.v1.planner.dao.Tag;
import com.projet.v1.planner.service.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("task/utils")
public class UtilsController {

    @Autowired
    private UtilsService utilsService;

    @PostMapping("create/compartiment")
    public ResponseObjectDto<Compartiment> createCompartiment(@RequestBody Compartiment compartiment){
        Compartiment response = utilsService.createCompartiment(compartiment);
        return new ResponseObjectDto<>(new ResponseDto("New compartiment created.", true) , response);
    }
    @PostMapping("create/tag")
    public ResponseObjectDto<Tag> createTag(@RequestBody Tag tag){
        Tag response = utilsService.createTag(tag);
        return new ResponseObjectDto<>(new ResponseDto("New tag created.", true) , response);
    }
    @GetMapping("read/tags")
    public ResponseObjectDto<List<Tag>> getTags(){
        return new ResponseObjectDto<>(null , utilsService.getAllTags());
    }
    @GetMapping("read/compartiments")
    public ResponseObjectDto<List<Compartiment>> getCompartiments(){
        return new ResponseObjectDto<>(null , utilsService.getAllCompartiments());
    }
    @PostMapping("update/tag")
    public ResponseObjectDto<Tag> updateTag(@RequestBody Tag tag) throws IncorrectRequestInformation {
        return new ResponseObjectDto<>(new ResponseDto("Tag has been updated.", true) , utilsService.updateTag(tag));
    }
    @PostMapping("update/compartiment")
    public ResponseObjectDto<Compartiment> updateCompartiment(@RequestBody Compartiment compartiment) throws IncorrectRequestInformation {
        return new ResponseObjectDto<>(new ResponseDto("Compartiment has been updated.", true)  , utilsService.updateCompartiment(compartiment));
    }
    @GetMapping("delete/tag/{id}")
    public ResponseDto deleteTag(@PathVariable("id") Integer id) throws IncorrectRequestInformation {
        utilsService.deleteTag(id);
        return new ResponseDto("Tag has been deleted.", true);
    }
    @GetMapping("delete/compartiment/{id}")
    public ResponseDto deleteCompartiment(@PathVariable("id") Integer id) throws IncorrectRequestInformation {
        utilsService.deleteCompartiment(id);
        return new ResponseDto("Compartiment has been deleted.", true) ;
    }
}
