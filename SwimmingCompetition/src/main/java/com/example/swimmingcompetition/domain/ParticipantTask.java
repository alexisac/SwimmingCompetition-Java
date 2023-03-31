package com.example.swimmingcompetition.domain;

public class ParticipantTask extends Entity<Integer>{
    public int idParticipant;
    public int idTask;


    /**
     * constructor for ParticipantTask class
     * @param idParticipant = Participant's ID
     * @param idTask = Task's ID
     */
    public ParticipantTask(int idParticipant, int idTask) {
        this.idParticipant = idParticipant;
        this.idTask = idTask;
    }

    /**
     * getter for ID participant
     * @return = participant's ID
     */
    public int getIdParticipant() {
        return idParticipant;
    }

    /**
     * setter for ID participant
     * @param idParticipant = the new ID for this participant
     */
    public void setIdParticipant(int idParticipant) {
        this.idParticipant = idParticipant;
    }

    /**
     * getter for ID task
     * @return = task's ID
     */
    public int getIdTask() {
        return idTask;
    }

    /**
     * setter for ID task
     * @param idTask = the new ID for this task
     */
    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }
}
