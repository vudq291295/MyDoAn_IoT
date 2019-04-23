package com.dqv.spring.oauth2.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "unit")
public class UnitBO {
	public int id;
	public String unitCode;
	public String name;
	public String adress;
	public String phone;
	public String nguoilienhe;
	
    @Id
    @Column(name = "id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
    @Column(name = "unit_code")
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	
    @Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "adress")
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "nguoilienhe")
	public String getNguoilienhe() {
		return nguoilienhe;
	}
	public void setNguoilienhe(String nguoilienhe) {
		this.nguoilienhe = nguoilienhe;
	}

	
}
