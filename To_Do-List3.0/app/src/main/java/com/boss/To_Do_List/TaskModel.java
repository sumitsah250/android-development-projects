package com.boss.To_Do_List;

public class TaskModel {
    String time;
    String task;
    String date;
    int ID;
    boolean status;

    public int getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = Integer.parseInt(ID);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public TaskModel(String task, String date, String time, boolean status, int ID) {
        this.time = time;
        this.task = task;
        this.date = date;
        this.ID = ID;
        this.status = status;
    }

    public TaskModel(String task, String date, String time, boolean status) {
        this.task = task;
        this.date = date;
        this.time = time;
        this.status=status;
    }
    public TaskModel() {
    }



}
