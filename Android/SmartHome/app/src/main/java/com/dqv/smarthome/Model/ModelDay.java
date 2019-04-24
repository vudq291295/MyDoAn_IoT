package com.dqv.smarthome.Model;

public class ModelDay {
    public String day;
    public boolean status;

    public ModelDay(String day, boolean status) {
        this.day = day;
        this.status = status;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
