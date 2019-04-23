package com.dqv.spring.oauth2.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dqv.spring.oauth2.DTO.UserDTO;


@Entity
@Table(name = "user")
public class UserBO {
    private static final long serialVersionUID = 1L;
    public int id;
    public String userName;
    public String passWord;
    public String fullName;
    public int type;
    public String unitCode;
    
    
//    @Override
//	public UserDTO toDTO() {
//    	UserDTO dto = new UserDTO();
//    	dto.setId(this.id);
//    	dto.setUserName(this.userName);
//    	dto.setPassWord(this.passWord);
//		// TODO Auto-generated method stub
//		return dto;
//	}

//	public UserBO() {
//           setColId("id");
//           setColName("id");
//           setUniqueColumn(new String[]{"id"});
//   }

    
    @Column(name = "unit_code")
    public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	@Id
    @Column(name = "id")
	public int getId() {
		return id;
	}

	@Column(name = "full_name")
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	
	@Column(name = "type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	

//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}

}
