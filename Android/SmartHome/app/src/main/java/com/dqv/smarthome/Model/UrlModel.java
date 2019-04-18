package com.dqv.smarthome.Model;

public class UrlModel {
    public static String domain = "http://14.160.26.174:6060/service-sh/";
    public static String iruMQTT = "tcp://14.160.26.174:1884";

//    public static String domain = "https://viefund.partnercenter.ca/VClient/VClient.asmx/";

    public static String Msg_DieSession = "Session expired";
    // link dang nhap vaf dang ki
    public static String url_login = domain + "oauth/token";// dang nhap
    public static String url_getCurrentUser = domain + "userinfo";// dang nhap
    // Room
    public static String url_getALlRoomn = domain + "api/room/getAllRoom";// dang nhap
    public static String url_insertRoomn = domain + "api/room/insertRoom";// dang nhap
    public static String url_updateRoomn = domain + "api/room/updateRoom";// dang nhap
    public static String url_deleteRoomn = domain + "api/room/deleteRoom";// dang nhap


    //equipment
    public static String url_getALlEquipment = domain + "api/equipment/getAllEpuipment";// dang nhap
    public static String url_insertEquipment = domain + "api/equipment/insertEpuipment";// dang nhap
    public static String url_updateEquipment = domain + "api/equipment/updateEpuipment";// dang nhap
    public static String url_deleteEquipment = domain + "api/equipment/deleteEpuipment";// dang nhap
    public static String url_getALlEquipmentByRoom = domain + "api/equipment/getEpuipmentByRoom/";// dang nhap

    //script
    public static String url_getALlScript = domain + "api/script/getAllScript";// dang nhap
    public static String url_insertScript = domain + "api/script/insertScript";// dang nhap
    public static String url_updateScript = domain + "api/script/updateScript";// dang nhap
    public static String url_deleteScript = domain + "api/script/deleteScript";// dang nhap
    public static String url_getDetailsScript = domain + "api/script/getDetailsScript/";// dang nhap

}
