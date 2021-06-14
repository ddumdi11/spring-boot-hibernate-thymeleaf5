package de.cmis.test.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TBL_CMISUSER")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CmisUserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "User_Name")
	private String userName;

	@Column(name = "User_Pwd")
	private String userPwd;

	@Column(name = "Create_Date")
	private String createDate;

	@Column(name = "User_Status")
	private String userStatus;

	public void createUSer(String userName, String userPwd) {
		this.userName = userName;
		this.userPwd = userPwd;
		setCreateDate();
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	
	public String getUserPwd() {
		return userPwd;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	
	public String getUserStatus() {
		return userStatus;
	}

	public void setCreateDate() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");
		this.createDate = now.format(df);
	}
	
	@Override
    public String toString() {
        return "UserEntity [id=" + id + ", userName=" + userName + 
                ", userPwd=" + userPwd + ", userStatus =" + userStatus   + "]";
    }

}
