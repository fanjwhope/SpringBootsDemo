package com.gutai.model.bo;

import com.gutai.Annotation.FieldFormatter;
import com.gutai.Annotation.PrimaryKey;
import com.gutai.Annotation.Table;
import com.gutai.Annotation.TableField;

/**
 * 使用
 * Created by 82421 on 2017/10/16.
 */
@Table(name = "USER_INFO")
public class TestBo1 {
    @PrimaryKey
    @TableField(name="Id")
    private String Id;
    @PrimaryKey
    @TableField(name = "name")
    private String username;
    @TableField(name="password1")
    private String password;
    private String remark;
    private String ABC_NN;


    public String getABC_NN() {
        return ABC_NN;
    }

    public void setABC_NN(String ABC_NN) {
        this.ABC_NN = ABC_NN;
    }

    @FieldFormatter(formatter = "yyyy-MM-dd HH:mi:ss",isDate = true)
    //yyyy-mm-dd hh24:mi:ss
    @PrimaryKey
    private String activeDate;
    @FieldFormatter(formatter = "2")
    private double salary;

    private int age;

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(String activeDate) {
        this.activeDate = activeDate;
    }

    public TestBo1(String id, String username, String password, String remark, String activeDate, double salary, int age) {
        Id = id;
        this.username = username;
        this.password = password;
        this.remark = remark;
        this.activeDate = activeDate;
        this.salary = salary;
        this.age = age;
    }

    public TestBo1() {
        super();
    }
}
