package com.example.swimmingcompetition.domain;

public class Validate {

    /**
     * verify if the username has more than 8 characters and doesn't have only white space / blanks
     * @param username = verified username
     * @return TRUE if the validation is passed, else FALSE
     */
    public boolean validateUsername(String username){
        return !username.isBlank() && username.length() > 7;
    }

    /**
     * verify if the password has more than 8 characters and doesn't have only white space / blanks
     * @param password = verified password
     * @return TRUE if the validation is passed, else FALSE
     */
    public boolean validatePassword(String password){
        return !password.isBlank() && password.length() > 7;
    }

    /**
     * verify if the name has more than 4 characters and doesn't have only white space / blanks
     * @param name = verified name
     * @return TRUE if the validation is passed, else FALSE
     */
    public boolean validateName(String name){
        return !name.isBlank() && name.length() > 3;
    }

    /**
     * verify if the age is in the range (3, 100)
     * @param age = verified age
     * @return TRUE if the validation is passed, else FALSE
     */
    public boolean validateAge(int age){
        return age > 3 && age < 100;
    }
}
