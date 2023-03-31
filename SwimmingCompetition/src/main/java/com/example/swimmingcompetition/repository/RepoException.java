package com.example.swimmingcompetition.repository;

public class RepoException extends Exception {

    String myMessage;


    /**
     * constructor
     */
    public RepoException(String myMessage) {
        this.myMessage = myMessage;
    }


    /**
     * getter for message
     * @return - Exception's message
     */
    public String getMyMessage() {
        return myMessage;
    }

}
