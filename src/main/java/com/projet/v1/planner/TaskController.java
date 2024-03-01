package com.projet.v1.planner;


import com.projet.v1.dto.ResponseDto;
import com.projet.v1.dto.ResponseObjectDto;
import com.projet.v1.exception.IncorrectRequestInformation;
import com.projet.v1.planner.dto.TaskCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("")
    public String test(){
        return "test";
    }

    @PostMapping(path = "create")
    public ResponseObjectDto createNewTask(@RequestBody TaskCreationRequest taskCreationRequest) throws IncorrectRequestInformation {
        if(taskCreationRequest != null) {
            TaskDao task = taskService.createNewTask(taskCreationRequest);
            return new ResponseObjectDto(new ResponseDto("The new task has been created", true), task);
        }else{
            throw new IncorrectRequestInformation("Task cant be created, title is missing.");
        }


    }
}
