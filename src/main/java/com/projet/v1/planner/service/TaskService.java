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
        taskToBeUpdated.setConmentaires(task.getConmentaires());
        taskToBeUpdated.setDateEcheance(task.getDateEcheance());
        taskToBeUpdated.setItems(task.getItems());
        taskToBeUpdated.setTag(task.getTag());
        taskToBeUpdated.setText(task.getText());
        taskToBeUpdated.setTaskorder(task.getTaskorder());

        return taskRepository.save(taskToBeUpdated);
    }

    public void updateTaskAfterDrag(TaskDao task) throws IncorrectRequestInformation {
        if (!verifyTask(task)) throw new IncorrectRequestInformation("This task is not correctly built.");
        TaskDao taskToBeUpdated = taskRepository.findById(task.getTaskId()).orElseThrow();
        log.info("----------------");

        boolean compartimentChanged = !Objects.equals(taskToBeUpdated.getCompartiment().getCompartimentId(), task.getCompartiment().getCompartimentId());
        boolean orderChanged = !Objects.equals(taskToBeUpdated.getTaskorder(), task.getTaskorder());
        log.info("compChanged " + compartimentChanged);
        log.info("orderChanged " + orderChanged);
        if(!compartimentChanged && !orderChanged){
            //nothing Changed
            this.updateTask(task);
        } else if (!compartimentChanged) {
            List<TaskDao> tasks = this.taskRepository.findByCompartiment(taskToBeUpdated.getCompartiment());
            List<TaskDao> tasksOrderChanged = this.updateTasksOrder(tasks, task, taskToBeUpdated);
            this.updateTasks(tasksOrderChanged);
        }

    }

    private List<TaskDao> updateTasksOrder(List<TaskDao> tasks, TaskDao task, TaskDao taskToBeUpdated){
        if(tasks.contains(taskToBeUpdated)){
            // sort array
            tasks.sort(new TaskDaoComparator());

            //replace in list old task by new one
            tasks.set(tasks.indexOf(taskToBeUpdated), task);


            Integer oldOrder = 0;
            boolean adapt = false;
            for(TaskDao t: tasks){
                if(Objects.equals(t.getTaskorder(), oldOrder) && !adapt) adapt = true;
                if(adapt){
                    t.setTaskorder(t.getTaskorder() + 1);
                }
            }

            int i = 1;
            boolean adapt2 = false;
            for(TaskDao t: tasks){
                if(t.getTaskorder() > i && !adapt2) adapt2 = true;

                if(adapt2){
                    t.setTaskorder(t.getTaskorder() - 1);
                }
                i++;
            }


        }
        return tasks;
    }

    private void updateTasks(List<TaskDao> tasks) throws IncorrectRequestInformation {
        for(TaskDao task: tasks){
            this.updateTask(task);
        }
    }

    private boolean verifyTask(TaskDao task){
        if(task.getDateCreation() == null) return false;
        if(!verifService.compartimentExist(task)) return false;
        return verifService.tagExist(task);
    }

    public List<TaskDao> getTasksByCompartiment(Integer compartimentId){
        Compartiment comp = this.compartimentRepository.findById(compartimentId).orElseThrow();
        return this.taskRepository.findByCompartiment(comp);
    }
}

class TaskDaoComparator implements java.util.Comparator<TaskDao> {
    @Override
    public int compare(TaskDao a, TaskDao b) {
        return a.getTaskorder() - b.getTaskorder();
    }
}
