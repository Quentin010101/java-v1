package com.projet.v1.module.planner;


import com.projet.v1.dto.ResponseDto;
import com.projet.v1.dto.ResponseObjectDto;
import com.projet.v1.exception.IncorrectRequestInformation;
import com.projet.v1.module.planner.dao.TaskDao;
import com.projet.v1.module.planner.dto.TaskCreationRequest;
import com.projet.v1.module.planner.service.TaskService;
import com.projet.v1.security.administration.ModuleService;
import com.projet.v1.security.administration.userConfiguration.ModuleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@CrossOrigin("${frontend.server.url}")
@RequestMapping("/task")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ModuleService moduleService;

    @PostMapping("/create")
    public ResponseObjectDto<TaskDao> createNewTask(@RequestBody TaskCreationRequest taskCreationRequest) throws IncorrectRequestInformation, AccessDeniedException {
        if(!moduleService.hasModule(ModuleEnum.PLANNER)) throw new AccessDeniedException("Planner module missing.");

        if(taskCreationRequest != null) {
            TaskDao task = taskService.createNewTask(taskCreationRequest);
            return new ResponseObjectDto<>(new ResponseDto("The new task has been created", true), task);
        }else{
            throw new IncorrectRequestInformation("Task cant be created, title is missing.");
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseDto deleteTask(@PathVariable("id") Integer id) throws IncorrectRequestInformation, AccessDeniedException {
        if(!moduleService.hasModule(ModuleEnum.PLANNER)) throw new AccessDeniedException("Planner module missing.");

        taskService.deleteTask(id);
        return new ResponseDto("the task has been deleted.", true);
    }

    @GetMapping("/read")
    public ResponseObjectDto<List<TaskDao>> getAllTasks() throws AccessDeniedException {
        if(!moduleService.hasModule(ModuleEnum.PLANNER)) throw new AccessDeniedException("Planner module missing.");

        List<TaskDao> tasks = taskService.getAllTask();
        log.info("Get all" + tasks.toString());
        return new ResponseObjectDto<List<TaskDao>>(new ResponseDto("", true) , tasks);
    }

    @PostMapping("/update")
    public ResponseObjectDto<TaskDao> updateTask(@RequestBody TaskDao task) throws IncorrectRequestInformation, AccessDeniedException {
        if(!moduleService.hasModule(ModuleEnum.PLANNER)) throw new AccessDeniedException("Planner module missing.");

        log.info(task.toString());
        if(task.getTitle().isBlank() || task.getTaskId() == null){
            throw new IncorrectRequestInformation("Task cant be updated, task is missing data.");
        }
        TaskDao response = taskService.updateTask(task);
        log.info(task.toString());
        return new ResponseObjectDto<>(new ResponseDto("The task has been updated.", true), response);
    }

    @PostMapping("/updateDragEvent")
    public ResponseObjectDto<List<TaskDao>> updateTaskAfterDrag(@RequestBody List<List<TaskDao>> tasksList) throws IncorrectRequestInformation, AccessDeniedException {
        if(!moduleService.hasModule(ModuleEnum.PLANNER)) throw new AccessDeniedException("Planner module missing.");

        if(tasksList.isEmpty()){
            throw new IncorrectRequestInformation("Tasks list is empty.");
        }
        taskService.updateListTasks(tasksList);
        return new ResponseObjectDto<>(new ResponseDto("Tasks updated after drop", true), taskService.getAllTask());
    }

    @ExceptionHandler(IncorrectRequestInformation.class)
    public ResponseEntity<String> handle(IncorrectRequestInformation ex){

        return new ResponseEntity<>(ex.getMessage(), HttpStatusCode.valueOf(650));
    }
}
