package com.example.swimmingcompetition.service;

import com.example.swimmingcompetition.domain.Task;
import com.example.swimmingcompetition.repository.RepoBDTask;

import java.util.Vector;

public class ServiceTask {
    RepoBDTask rt;


    /**
     * constructor
     * @param rt - RepoBDTask
     */
    public ServiceTask(RepoBDTask rt) {
        this.rt = rt;
    }


    /**
     * Search a task by ID in database
     * @param idTask - searched task's ID
     * @return - the task if there exist, else null
     */
    public Task findOne(int idTask){
        return rt.findOne(idTask);
    }


    /**
     * @return - an array with all tasks from the database
     */
    public Vector<Task> findAll(){
        return rt.findAll();
    }
}
