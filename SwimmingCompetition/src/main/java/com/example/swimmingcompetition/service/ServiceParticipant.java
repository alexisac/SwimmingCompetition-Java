package com.example.swimmingcompetition.service;

import com.example.swimmingcompetition.domain.Participant;
import com.example.swimmingcompetition.domain.Validate;
import com.example.swimmingcompetition.repository.RepoBDParticipant;

import java.util.Collections;
import java.util.Vector;

public class ServiceParticipant {

    RepoBDParticipant rp;
    Validate v;


    /**
     * constructor
     * @param rp - RepoBDParticipant
     * @param v - Validate
     */
    public ServiceParticipant(RepoBDParticipant rp, Validate v) {
        this.rp = rp;
        this.v = v;
    }


    /**
     * it takes all participants IDs from database, and it releases an ID never used from the range [0, infinite)
     * @return - ID found
     */
    private int giveNewId(){
        Vector<Integer> vect = new Vector<Integer>();
        for (Participant a: rp.findAll()) {
            vect.add(a.getId());
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
     * add a participant in database
     * @param name - participant's name
     * @param age - participant's age
     * @throws ServiceException - "Name and age are wrong!" / "Name is wrong!" / "Age is wrong!"
     */
    public void add(String name, int age) throws ServiceException{
        if (!rp.ifExistParticipant(new Participant(name, age))) {
            if (v.validateName(name) && v.validateAge(age)) {
                Participant p = new Participant(name, age);
                p.setId(giveNewId());
                rp.add(p);
            } else if (!v.validateName(name) && !v.validateAge(age)) {
                throw new ServiceException("Name and age are wrong!");
            } else if (!v.validateName(name)) {
                throw new ServiceException("Name is wrong!");
            } else {
                throw new ServiceException("Age is wrong!");
            }
        } else {
            throw new ServiceException("This participant already exist");
        }
    }


    /**
     * delete a participant from database
     * @param id - participant's id
     */
    public void delete(int id){
        rp.delete(id);
    }


    /**
     * Search a participant by ID in database
     * @param idParticipant - participant's id
     * @return - the participant if there exist, else null
     */
    public Participant findOne(int idParticipant){
        return rp.findOne(idParticipant);
    }


    /**
     * @return - an array with all participants from the database
     */
    public Vector<Participant> findAll(){
        return rp.findAll();
    }
}
