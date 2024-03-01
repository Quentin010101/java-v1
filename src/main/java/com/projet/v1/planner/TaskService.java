package com.projet.v1.planner;

import com.projet.v1.exception.IncorrectRequestInformation;
import com.projet.v1.planner.dto.TaskCreationRequest;
import com.projet.v1.planner.enumeration.Importance;
import com.projet.v1.planner.enumeration.Progression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public TaskDao createNewTask(TaskCreationRequest taskCreationRequest) throws IncorrectRequestInformation {
        String title = taskCreationRequest.title();
        title.trim();
        if(title.length() < 1){
            throw new IncorrectRequestInformation("The task's title is required.");
        }
        TaskDao task = new TaskDao();
        task.setTitle(taskCreationRequest.title());
        task.setDateCreation(new Date());
        task.setImportance(Importance.Default);
        task.setProgression(Progression.DEFAULT);
        return taskRepository.save(task);
    }
}
