package com.dqv.smarthome.Model;

import java.sql.Time;

public class ScheduleModel {
    public int id;
    public String name;
    public String timeStart;
    public String timeEnd;
    public String unitCode;
    public int equipmentID;
    public int scriptID;
    public String day;
    public int status;
    public int statusEquipment;

    public String equipmentName;
    public String scriptName;

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
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

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public int getScriptID() {
        return scriptID;
    }

    public void setScriptID(int scriptID) {
        this.scriptID = scriptID;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatusEquipment() {
        return statusEquipment;
    }

    public void setStatusEquipment(int statusEquipment) {
        this.statusEquipment = statusEquipment;
    }
}
