package com.gutai.model.bo;

import com.gutai.Annotation.FieldFormatter;
import com.gutai.Annotation.Table;
import com.gutai.Annotation.TableField;
import java.util.*;

/**
 * Created by 82421 on 2017/10/16.
 */
@Table(name="myTable")
public class TestBo {
    @TableField(name="myId")
    private String Id;
    private String username;
    private String password;
    private String remark;
    @FieldFormatter(formatter = "yyyy-MM-dd HH:mm:ss")
    private Date activeDate;

    public Date getActiveDate() {
        return  activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }
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

    /**
     *
     * @param id
     * @param username
     * @param password
     * @param remark
     * @param activeDate
     * @param salary
     * @param age
     */
    public TestBo(String id, String username, String password, String remark, Date activeDate, double salary, int age) {
        Id = id;
        this.username = username;
      //  this.password = password;
        this.remark = remark;
        this.activeDate = activeDate;
        this.salary = salary;
        this.age = age;
    }

    public TestBo() {
        super();
    }
}
