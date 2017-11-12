package com.kaishengit.crm.entity;

public class Dept {
    private Integer id;

    private String deptName;

    private Integer pidId;
    private static final long serialVersionUID = 1L;

    public Account getAcount() {
        return account;
    }

    public void setAcount(Account acount) {
        this.account = account;
    }

    private  Account account;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getPidId() {
        return pidId;
    }

    public void setPidId(Integer pidId) {
        this.pidId = pidId;
    }
}