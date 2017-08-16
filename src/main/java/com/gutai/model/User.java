package com.gutai.model;

/**
 * Created by 82421 on 2017/8/14.
 */
public class User {
    private String name;
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User(){
        super();
    }

    public User(String name, Integer id) {
        this.name = name;
        this.id = id;
    }
}
