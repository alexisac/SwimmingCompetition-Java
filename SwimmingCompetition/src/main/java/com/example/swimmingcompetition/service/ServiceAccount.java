package com.example.swimmingcompetition.service;

import com.example.swimmingcompetition.domain.Account;
import com.example.swimmingcompetition.domain.Validate;
import com.example.swimmingcompetition.repository.RepoBDAccount;

import java.util.Collections;
import java.util.Vector;

public class ServiceAccount {

    RepoBDAccount repoAC;
    Validate v;


    /**
     * it takes all accounts IDs from database, and it releases an ID never used from the range [0, infinite)
     * @return - ID found
     */
    private int giveNewId(){
        Vector<Integer> vect = new Vector<Integer>();
        for (Account a: repoAC.findAll()) {
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
     * constructor
     * @param repoAC - RepoBDAccount
     * @param v - Validate
     */
    public ServiceAccount(RepoBDAccount repoAC, Validate v) {
        this.repoAC = repoAC;
        this.v = v;
    }


    /**
     * verify if this account is in database or not
     * @param username - account's username
     * @param password - account's password
     * @return - account's ID if there is one, else -1
     */
    public int ifExistAccount(String username, String password){
        return repoAC.ifExistAccount(new Account(username, password));
    }


    /**
     * add an account in database
     * @param username - account's username
     * @param password - account's password
     * @throws ServiceException - "Username and password are wrong!" / "Username is wrong!" / "Password is wrong!"
     */
    public void add(String username, String password) throws ServiceException{
        if(v.validateUsername(username) && v.validatePassword(password)){
            Account a = new Account(username, password);
            a.setId(giveNewId());
            repoAC.add(a);
        } else {
            if(!v.validateUsername(username) && !v.validatePassword(password)) {
                throw new ServiceException("Username and password are wrong!");
            } else if (!v.validateUsername(username)){
                throw new ServiceException("Username is wrong!");
            } else {
                throw new ServiceException("Password is wrong!");
            }
        }
    }


    /**
     * Search an account by ID in database
     * @param id - account's id
     * @return - the account if there exist, else null
     */
    public Account findOne(int id){
        return repoAC.findOne(id);
    }
}
