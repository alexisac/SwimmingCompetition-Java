package com.example.swimmingcompetition.domain;

public class Participant extends Entity<Integer> {

    public String name;
    public int age;


    /**
     * constructor for Participant class
     * @param name = participant's name
     * @param age = participant's age
     */
    public Participant(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * getter for name
     * @return = participant's name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for name
     * @param name = the new name for this participant
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for age
     * @return = participant's age
     */
    public int getAge() {
        return age;
    }

    /**
     * setter for age
     * @param age = the new age of this participant
     */
    public void setAge(int age) {
        this.age = age;
    }
}

