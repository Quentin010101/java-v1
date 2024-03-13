package com.projet.v1.planner;


import com.projet.v1.dto.ResponseDto;
import com.projet.v1.dto.ResponseObjectDto;
import com.projet.v1.exception.IncorrectRequestInformation;
import com.projet.v1.planner.dao.TaskDao;
import com.projet.v1.planner.dto.TaskCreationRequest;
import com.projet.v1.planner.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("${frontend.server.url}")
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseObjectDto<TaskDao> createNewTask(@RequestBody TaskCreationRequest taskCreationRequest) throws IncorrectRequestInformation {
        if(taskCreationRequest != null) {
            TaskDao task = taskService.createNewTask(taskCreationRequest);
            return new ResponseObjectDto<>(new ResponseDto("The new task has been created", true), task);
        }else{
            throw new IncorrectRequestInformation("Task cant be created, title is missing.");
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseDto deleteTask(@PathVariable("id") Integer id) throws IncorrectRequestInformation {
        taskService.deleteTask(id);
        return new ResponseDto("the task has been deleted.", true);
    }

    @GetMapping("/read")
    public ResponseObjectDto<List<TaskDao>> getAllTasks()  {
        List<TaskDao> tasks = taskService.getAllTask();
        return new ResponseObjectDto<List<TaskDao>>(null , tasks);
    }

    @PostMapping("/update")
    public ResponseObjectDto<TaskDao> updateTask(@RequestBody TaskDao task) throws IncorrectRequestInformation {
        if(task.getTitle().isBlank() || task.getTaskId() == null)
            throw new IncorrectRequestInformation("Task cant be updated, task is missing data.");
        TaskDao response = taskService.updateTask(task);
        return new ResponseObjectDto<TaskDao>(new ResponseDto("The task has been updated.", true), response);
    }

    @ExceptionHandler(IncorrectRequestInformation.class)
    public ResponseEntity<String> handle(IncorrectRequestInformation ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
