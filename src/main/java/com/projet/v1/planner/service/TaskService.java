package com.projet.v1.planner.service;

import com.projet.v1.exception.IncorrectRequestInformation;
import com.projet.v1.planner.repository.TaskRepository;
import com.projet.v1.planner.dao.TaskDao;
import com.projet.v1.planner.dto.TaskCreationRequest;
import com.projet.v1.planner.enumeration.Importance;
import com.projet.v1.planner.enumeration.Progression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Slf4j
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private VerifService verifService;

    public TaskDao createNewTask(TaskCreationRequest taskCreationRequest) throws IncorrectRequestInformation {
        String title = taskCreationRequest.title();

        if(title.trim().isEmpty()){
            throw new IncorrectRequestInformation("The task's title is required.");
        }
        if(!verifService.compartimentExist(taskCreationRequest.compartiment())){
            throw new IncorrectRequestInformation("The task's compartiment is required.");
        }
        TaskDao task = new TaskDao();
        task.setTitle(taskCreationRequest.title());
        task.setDateCreation(new Date());
        task.setImportance(Importance.DEFAULT.getId());
        task.setProgression(Progression.DEFAULT.getId());
        task.setCompartiment(taskCreationRequest.compartiment());
        return taskRepository.save(task);
    }

    public void deleteTask(Integer id) throws IncorrectRequestInformation {
        if(!taskRepository.existsById(id)) throw new IncorrectRequestInformation("This task doesn't exist.");
        taskRepository.deleteById(id);
    }

    public List<TaskDao> getAllTask(){
        return taskRepository.findAll();
    }

    public TaskDao updateTask(TaskDao task) throws IncorrectRequestInformation {
        if(!verifyTask(task)) throw new IncorrectRequestInformation("This task is not correctly built.");
        TaskDao taskToBeUpdated = taskRepository.findById(task.getTaskId()).orElseThrow();

        taskToBeUpdated.setCompartiment(task.getCompartiment());
        taskToBeUpdated.setImportance(task.getImportance());
        taskToBeUpdated.setProgression(task.getProgression());
        taskToBeUpdated.setConmentaires(task.getConmentaires());
        taskToBeUpdated.setDateEcheance(task.getDateEcheance());
        taskToBeUpdated.setItems(task.getItems());
        taskToBeUpdated.setTag(task.getTag());
        taskToBeUpdated.setText(task.getText());

        return taskRepository.save(taskToBeUpdated);
    }


    private boolean verifyTask(TaskDao task){
        if(task.getDateCreation() == null) return false;
        log.info("1");
        if(!verifService.compartimentExist(task)) return false;
        log.info("2");
        if(!verifService.tagExist(task)) return false;
        log.info("3");

        return true;
    }
}
