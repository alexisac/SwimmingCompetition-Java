package com.example.swimmingcompetition.domain;

public class Utility {


    /**
     * enum type for task's distance
     */
    public enum enumDistance {
        A(50), B(200), C(800), D(1500);

        private int dist;

        // constructor
        enumDistance(int dist){
            this.dist = dist;
        }

        /**
         * getter for distance
         * @return distance
         */
        public int getDist(){
            return dist;
        }
    }


    /**
     * enum type for task's style
     */
    public enum enumStyle {FREESTYLE, BACKSTROKE, BUTTERFLY, MIXT}
}
