package com.dqv.servicechecktime.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.EquipmentModel;
import Model.SchedueModel;

public class ScheduleJDBC {

	Connection con;
	public ScheduleJDBC(Connection con) {
		this.con = con;
	}
	
	public List<EquipmentModel> getEquipSet(){
		Date in = new Date();
		LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
		Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

		List<EquipmentModel> result = new ArrayList<EquipmentModel>();
		List<SchedueModel> resultSchedule = new ArrayList<SchedueModel>();

		String QUERY = "select * from schedule where time_start = '"+ldt.getHour()+":"+ldt.getMinute()
						+"' and  (day is null OR day like '%"+out.getDay()+"%' OR day = '')"
						+" and status = 1";
		System.out.println(QUERY);
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(QUERY);	
			while(rs.next()){
				SchedueModel tempSchedule = new SchedueModel();
				tempSchedule.setScript_id(rs.getInt("script_id"));
				tempSchedule.setEquipment_id(rs.getInt("equipment_id"));
				tempSchedule.setStatusEquipment(rs.getInt("status_equip"));
				tempSchedule.setDay(rs.getString("day"));
				tempSchedule.setId(rs.getInt("id"));
				resultSchedule.add(tempSchedule);
			}
			System.out.println(resultSchedule.size());
			if(resultSchedule.size() > 0) {
				for(int i = 0;i<resultSchedule.size();i++) {
					if(resultSchedule.get(i).getEquipment_id()>0) {
						String QUERY_EQUIPT = "select * from equipment a"
											+" INNER JOIN room b"
											+" ON a.room_id = b.id"
											+" WHERE a.id = "+resultSchedule.get(i).getEquipment_id();
						rs = stmt.executeQuery(QUERY_EQUIPT);	
						while(rs.next()){
							EquipmentModel tempEquipt = new EquipmentModel();
							tempEquipt.setChanel(rs.getInt("chanel"));
							tempEquipt.setPortOutput(rs.getInt("port_output"));
							tempEquipt.setStatus(resultSchedule.get(i).getStatusEquipment());
							result.add(tempEquipt);
						}
					}
					if(resultSchedule.get(i).getScript_id()>0) {
						String QUERY_EQUIPT = "select chanel,port_output,c.status from equipment a" + 
								" INNER JOIN room b" + 
								" ON a.room_id = b.id" + 
								"  INNER JOIN script_has_equipment c" + 
								" ON a.id = c.equipment_id"
								+" WHERE a.id in (select equipment_id from script_has_equipment where script_id = "+resultSchedule.get(i).getScript_id()+")";
						rs = stmt.executeQuery(QUERY_EQUIPT);	
						while(rs.next()){
							EquipmentModel tempEquipt = new EquipmentModel();
							tempEquipt.setChanel(rs.getInt("chanel"));
							tempEquipt.setPortOutput(rs.getInt("port_output"));
							tempEquipt.setStatus(rs.getInt("status"));
							result.add(tempEquipt);
						}						
					}
					if(resultSchedule.get(i).getDay().isEmpty()) {
						String QUERY_UPDATE = "UPDATE schedule set status = 0"
								+" WHERE id = "+resultSchedule.get(i).getId();
						stmt.executeUpdate(QUERY_UPDATE);
					}
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

}
