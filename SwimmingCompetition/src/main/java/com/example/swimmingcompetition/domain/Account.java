package com.example.swimmingcompetition.domain;

public class Account extends Entity<Integer>{
    public String username;
    public String password;


    /**
     * constructor for Account class
     * @param username = account's username
     * @param password = account's password
     */
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * getter for username
     * @return = account's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * setter for username
     * @param username = the new username for this account
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getter for password
     * @return = account's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter for password
     * @param password = the new password for this account
     */
    public void setPassward(String password) {
        this.password = password;
    }
}

