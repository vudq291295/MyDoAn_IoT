package com.dqv.smarthome.Model;

public class UrlModel {
    public static String domain = "http://192.168.100.7:8080/service-sh/";
    public static String iruMQTT = "tcp://192.168.100.7:1884";

//    public static String domain = "https://viefund.partnercenter.ca/VClient/VClient.asmx/";

    public static String Msg_DieSession = "Session expired";
    // link dang nhap vaf dang ki
    public static String url_login = domain + "oauth/token";// dang nhap
    public static String url_getCurrentUser = domain + "userinfo";// dang nhap
    // Room
    public static String url_getALlRoomn = domain + "api/room/getAllRoom";// dang nhap


    //equipment
    public static String url_getALlEquipment = domain + "api/equipment/getAllEpuipment";// dang nhap

}
