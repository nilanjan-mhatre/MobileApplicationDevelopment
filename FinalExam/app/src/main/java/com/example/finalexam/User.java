package com.example.finalexam;

import java.io.Serializable;

/**
 * Created by Nilanjan Mhatre
 * EmailObj: nmhatre@uncc.edu
 * Student Id: 801045013
 */

public class User implements Serializable, Cloneable {

    String userId;

    String fullName;

    String email;

    String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String userId, String firstName, String lastName) {
        this.userId = userId;
        this.fullName = firstName + " " + lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
