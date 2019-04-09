package com.dqv.smarthome.Model;

public class RoomModel {
    public String name;
    public int countEquipment,chanel;
    public int id;

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

    public int getCountEquipment() {
        return countEquipment;
    }

    public void setCountEquipment(int countEquipment) {
        this.countEquipment = countEquipment;
    }

    public int getChanel() {
        return chanel;
    }

    public void setChanel(int chanel) {
        this.chanel = chanel;
    }
}
