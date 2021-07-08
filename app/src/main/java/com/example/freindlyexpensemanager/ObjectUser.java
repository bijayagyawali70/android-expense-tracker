package com.example.freindlyexpensemanager;

public class ObjectUser {
    int id;
    String username;
    float amount;

    public ObjectUser(){

    }



    public ObjectUser(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public ObjectUser(int id, String username, Float amount) {
        this.id = id;
        this.username = username;
        this.amount = amount;
    }

    public ObjectUser(int id, float amount) {
        this.id = id;
        this.amount = amount;
    }

    public ObjectUser(String username) {
        this.username = username;
    }

    public ObjectUser(float amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
