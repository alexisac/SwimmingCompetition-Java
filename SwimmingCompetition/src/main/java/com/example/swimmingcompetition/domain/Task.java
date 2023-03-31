package com.example.swimmingcompetition.domain;

public class Task extends Entity<Integer> {

    public Utility.enumDistance distance;
    public Utility.enumStyle style;


    /**
     * constructor for Task class
     * @param distance = task's distance
     * @param style = task's style
     */
    public Task(Utility.enumDistance distance, Utility.enumStyle style) {
        this.distance = distance;
        this.style = style;
    }

    /**
     * getter for distance
     * @return = task's distance
     */
    public Utility.enumDistance getDistance() {
        return distance;
    }

    /**
     * setter for distance
     * @param distance = the new distance for this task
     */
    public void setDistance(Utility.enumDistance distance) {
        this.distance = distance;
    }

    /**
     * getter for style
     * @return = task's style
     */
    public Utility.enumStyle getStyle() {
        return style;
    }

    /**
     * setter for style
     * @param style = the style for this task
     */
    public void setStyle(Utility.enumStyle style) {
        this.style = style;
    }
}
