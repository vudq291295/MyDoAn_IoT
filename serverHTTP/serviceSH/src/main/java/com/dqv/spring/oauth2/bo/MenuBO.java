package com.dqv.spring.oauth2.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menu")
public class MenuBO {
	public int id,trangthai;
	public String maMenu,tenMenu,maCha,sTT,icon;
	
	@Id
    @Column(name = "id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
    @Column(name = "trangthai")
	public int getTrangthai() {
		return trangthai;
	}
	public void setTrangthai(int trangthai) {
		this.trangthai = trangthai;
	}
	
    @Column(name = "ma_menu")
	public String getMaMenu() {
		return maMenu;
	}
	public void setMaMenu(String maMenu) {
		this.maMenu = maMenu;
	}
	
    @Column(name = "ten_menu")
	public String getTenMenu() {
		return tenMenu;
	}
	public void setTenMenu(String tenMenu) {
		this.tenMenu = tenMenu;
	}
	
    @Column(name = "ma_cha")
	public String getMaCha() {
		return maCha;
	}
	public void setMaCha(String maCha) {
		this.maCha = maCha;
	}
	
    @Column(name = "stt")
	public String getsTT() {
		return sTT;
	}
	public void setsTT(String sTT) {
		this.sTT = sTT;
	}
	
    @Column(name = "icon")
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
	
	
	
}
