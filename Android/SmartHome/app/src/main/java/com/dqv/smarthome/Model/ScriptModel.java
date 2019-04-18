package com.dqv.smarthome.Model;

import java.util.ArrayList;
import java.util.List;

public class ScriptModel {
    public int id;
    public String name;
    public List<ScriptDetailsModel> details;

    public ScriptModel() {
        details = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScriptDetailsModel> getDetails() {
        return details;
    }

    public void setDetails(List<ScriptDetailsModel> details) {
        this.details = details;
    }
}
