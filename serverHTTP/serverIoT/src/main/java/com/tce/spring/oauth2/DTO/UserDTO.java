package com.tce.spring.oauth2.DTO;

import javax.xml.bind.annotation.XmlRootElement;

import com.tce.spring.oauth2.bo.UserBO;

import vudq.service.base.dto.BaseFWDTOImpl;

@XmlRootElement(name = "USERBO")
public class UserDTO extends BaseFWDTOImpl<UserBO> {
	private static final long serialVersionUID = 1L;
    public int id;
    public String userName;
    public String passWord;

    public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassWord() {
		return passWord;
	}


	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
    public UserBO toModel() {
		UserBO result = new UserBO();
		result.setId(this.id);
		result.setUserName(this.userName);
		result.setPassWord(this.passWord);
    	return result;
    }


	@Override
	public String catchName() {
		// TODO Auto-generated method stub
        return getId()+"";
	}


	@Override
	public Long getFWModelId() {
		// TODO Auto-generated method stub
        return Long.valueOf(id);
	}
}
