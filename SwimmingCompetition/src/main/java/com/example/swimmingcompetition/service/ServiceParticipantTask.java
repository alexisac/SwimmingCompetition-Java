package com.example.swimmingcompetition.service;

import com.example.swimmingcompetition.domain.ParticipantTask;
import com.example.swimmingcompetition.repository.RepoBDParticipantTask;

import java.util.Collections;
import java.util.Vector;

public class ServiceParticipantTask {

    RepoBDParticipantTask rpt;

    /**
     * constructor
     * @param rpt - RepoBDParticipantTask
     */
    public ServiceParticipantTask(RepoBDParticipantTask rpt) {
        this.rpt = rpt;
    }


    /**
     * it takes all participantTasks IDs from database, and it releases an ID never used from the range [0, infinite)
     * @return - ID found
     */
    private int giveNewId(){
        Vector<Integer> vect = new Vector<Integer>();
        for (ParticipantTask pt: rpt.findAll()) {
            vect.add(pt.getId());
        }
        Collections.sort(vect);
        boolean gasit = false;
        int id = 1;
        int i = 0;
        while (!gasit && i < vect.size()) {
            if (id == vect.get(i)){
                i++;
                id ++;
            } else {
                gasit = true;
            }
        }
        return id;
    }


    /**
     * Search all participantTasks that have a task's ID
     * used to get the IDs of all participants participating in a task
     * (folosita pentru a lua id urile tuturor participantilor care participa la un task)
     * @param idTask - task's ID
     * @return - an array with all participantTasks from the database
     */
    public Vector<ParticipantTask> findAllParticipants(int idTask){
        return rpt.findAllParticipants(idTask);
    }


    /**
     * Search all participantTasks that have a participant's ID
     * used to get the IDs of all the tasks in which a participant participates
     * (folosita pentru a lua id urile tuturor taskurile la care participa un participant)
     * @param idParticipant - participant's ID
     * @return - an array with all participantTasks from the database
     */
    public Vector<ParticipantTask> findAllTasks(int idParticipant){
        return rpt.findAllTasks(idParticipant);
    }


    /**
     * add participantTask in database
     * @param idPArticipant - ParticipantTask's idParticipant
     * @param idTask - ParticipantTask's idTask
     */
    public void add(int idPArticipant, int idTask) throws ServiceException{
        ParticipantTask pt = new ParticipantTask(idPArticipant, idTask);
        if(!rpt.ifExist(pt)){
            pt.setId(giveNewId());
            rpt.add(pt);
        } else {
            throw new ServiceException("This participant particip at this task already");
        }
    }
}
