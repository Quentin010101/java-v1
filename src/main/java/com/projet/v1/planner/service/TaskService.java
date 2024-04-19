package com.projet.v1.planner.service;

import com.projet.v1.exception.IncorrectRequestInformation;
import com.projet.v1.planner.dao.Compartiment;
import com.projet.v1.planner.dto.TasksContainer;
import com.projet.v1.planner.repository.CompartimentRepository;
import com.projet.v1.planner.repository.TaskRepository;
import com.projet.v1.planner.dao.TaskDao;
import com.projet.v1.planner.dto.TaskCreationRequest;
import com.projet.v1.planner.enumeration.Importance;
import com.projet.v1.planner.enumeration.Progression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CompartimentRepository compartimentRepository;
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
        task.setTaskorder(setTaskOrder(taskCreationRequest));
        task.setTitle(taskCreationRequest.title());
        task.setDateCreation(new Date());
        task.setImportance(Importance.DEFAULT.getId());
        task.setProgression(Progression.DEFAULT.getId());
        task.setCompartiment(taskCreationRequest.compartiment());
        return taskRepository.save(task);
    }

    private Integer setTaskOrder(TaskCreationRequest task){
        List<TaskDao> listTask = taskRepository.findByCompartiment(compartimentRepository.findById(task.compartiment().getCompartimentId()).orElseThrow());
        Integer lastOrder;
        listTask.sort(Comparator.comparingInt(TaskDao::getTaskorder));
        for(TaskDao t : listTask){
            log.info(t.toString());
        }
        if(!listTask.isEmpty()){
            lastOrder = listTask.get(listTask.size() - 1).getTaskorder() + 1;
        }else {
            lastOrder = 1;
        }
        log.info(String.valueOf(lastOrder));
        return lastOrder;
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
        taskToBeUpdated.setCommentaires(task.getCommentaires());
        taskToBeUpdated.setDateEcheance(task.getDateEcheance());
        taskToBeUpdated.setItems(task.getItems());
        taskToBeUpdated.setTags(task.getTags());
        taskToBeUpdated.setText(task.getText());
        taskToBeUpdated.setTaskorder(task.getTaskorder());
        log.info("tags:" + taskToBeUpdated.getTags().toString());

        return taskRepository.save(taskToBeUpdated);
    }

    public void updateListTasks(List<List<TaskDao>> listTasks) throws IncorrectRequestInformation {
        for(List<TaskDao> tasks: listTasks){
            this.updateTasks(tasks);
        }
    }
    private void updateTasks(List<TaskDao> tasks) throws IncorrectRequestInformation {
        for(TaskDao task: tasks){
            this.updateTask(task);
        }
        taskRepository.flush();

    }

    private boolean verifyTask(TaskDao task){
        if(task.getDateCreation() == null) return false;
        if(task.getTaskorder() == null || task.getTaskorder() < 1) return false;
        if(!verifService.compartimentExist(task)) return false;
        return verifService.tagsExist(task);
    }

    public List<TaskDao> getTasksByCompartiment(Integer compartimentId){
        Compartiment comp = this.compartimentRepository.findById(compartimentId).orElseThrow();
        return this.taskRepository.findByCompartiment(comp);
    }
}


