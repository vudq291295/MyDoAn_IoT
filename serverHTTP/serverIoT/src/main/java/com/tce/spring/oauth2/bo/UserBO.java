package com.tce.spring.oauth2.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tce.spring.oauth2.DTO.UserDTO;

import vudq.service.base.model.BaseFWModelImpl;

@Entity
@Table(name = "user")
public class UserBO extends BaseFWModelImpl {
    private static final long serialVersionUID = 1L;
    public int id;
    public String userName;
    public String passWord;

    
    
    @Override
	public UserDTO toDTO() {
    	UserDTO dto = new UserDTO();
    	dto.setId(this.id);
    	dto.setUserName(this.userName);
    	dto.setPassWord(this.passWord);
		// TODO Auto-generated method stub
		return dto;
	}

	public UserBO() {
           setColId("id");
           setColName("id");
           setUniqueColumn(new String[]{"id"});
   }

    @Id
    @Column(name = "id")
	public int getId() {
		return id;
	}

	@Column(name = "username")
	public String getUserName() {
		return userName;
	}
	
	@Column(name = "password")
	public String getPassWord() {
		return passWord;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public void setId(int id) {
		this.id = id;
	}


//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}

}
