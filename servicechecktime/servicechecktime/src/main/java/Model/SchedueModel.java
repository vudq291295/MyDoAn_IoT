package Model;

import java.sql.Time;

public class SchedueModel {
	public int id;
	public String name;
	public Time time_start;
	public Time time_end;
	public String unit_code;
	public int equipment_id;
	public int script_id;
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
	public Time getTime_start() {
		return time_start;
	}
	public void setTime_start(Time time_start) {
		this.time_start = time_start;
	}
	public Time getTime_end() {
		return time_end;
	}
	public void setTime_end(Time time_end) {
		this.time_end = time_end;
	}
	public String getUnit_code() {
		return unit_code;
	}
	public void setUnit_code(String unit_code) {
		this.unit_code = unit_code;
	}
	public int getEquipment_id() {
		return equipment_id;
	}
	public void setEquipment_id(int equipment_id) {
		this.equipment_id = equipment_id;
	}
	public int getScript_id() {
		return script_id;
	}
	public void setScript_id(int script_id) {
		this.script_id = script_id;
	}
	
	
}
