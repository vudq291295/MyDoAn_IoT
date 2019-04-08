package com.dqv.smarthome.Model;

import java.io.Serializable;

public class UserModel implements Serializable {
    private static final long serialVersionUID = -7974823823497497357L;

    String id, name, pasword,token;

    public UserModel() {
    }

    public UserModel(String id, String name, String pasword, String token) {
        this.id = id;
        this.name = name;
        this.pasword = pasword;
        this.token = token;
    }

    public UserModel(String id, String name, String pasword) {
        this.id = id;
        this.name = name;
        this.pasword = pasword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
