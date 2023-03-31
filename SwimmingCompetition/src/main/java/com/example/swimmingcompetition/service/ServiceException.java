package com.example.swimmingcompetition.service;

public class ServiceException extends Exception {

    String myMessage;


    /**
     * constructor
     */
    public ServiceException(String myMessage) {
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
