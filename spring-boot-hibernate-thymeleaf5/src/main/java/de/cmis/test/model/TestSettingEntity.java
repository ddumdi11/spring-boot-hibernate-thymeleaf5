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
@Table(name = "TBL_SETTING")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TestSettingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "User_Id")
	private Long userId;

	@Column(name = "Binding_Id")
	private Long bindingId;

	@Column(name = "Is_Active")
	private boolean isActive;

	@Column(name = "Setting_Date")
	private String settingDate;

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setBindingId(Long bindingId) {
		this.bindingId = bindingId;
	}

	public Long getBindingId() {
		return bindingId;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setSettingDate() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");
		this.settingDate = now.format(df);
	}

}
