package com.dqv.smarthome.Model;

public class ScriptDetailsModel {
    public int scripID;
    public int equipmentID;
    public int status;
    public String equipmentName;
    public int equipmentPort;
    public int equipmentChanel;
    public int roomID;
    public String roomName;

    public int getScripID() {
        return scripID;
    }

    public void setScripID(int scripID) {
        this.scripID = scripID;
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public int getEquipmentPort() {
        return equipmentPort;
    }

    public void setEquipmentPort(int equipmentPort) {
        this.equipmentPort = equipmentPort;
    }

    public int getEquipmentChanel() {
        return equipmentChanel;
    }

    public void setEquipmentChanel(int equipmentChanel) {
        this.equipmentChanel = equipmentChanel;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
