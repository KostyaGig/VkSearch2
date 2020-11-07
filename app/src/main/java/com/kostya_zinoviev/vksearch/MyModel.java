package com.kostya_zinoviev.vksearch;

public class MyModel {
    int id;
    String name;
    String lastName;

    public MyModel(int id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }
}
