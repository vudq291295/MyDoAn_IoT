package com.dqv.spring.oauth2.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "group_menu")
public class GroupMenuBO {
	public int id;
	public int groupId;
	public int menuId;
	
    @Id
    @Column(name = "id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
    @Column(name = "group_id")
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
    @Column(name = "menu_id")
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	
	
}
