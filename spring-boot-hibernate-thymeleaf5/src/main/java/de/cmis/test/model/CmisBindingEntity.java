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

@Entity
@Table(name = "TBL_CMISBINDING")
@Getter
@Setter
@NoArgsConstructor
public class CmisBindingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Binding_Name")
	private String bindingName;

	@Column(name = "Binding_Url")
	private String bindingUrl;

	@Column(name = "Create_Date")
	private String createDate;


	public void createBinding(String bindingName, String bindingUrl) {
		this.bindingName = bindingName;
		this.bindingUrl = bindingUrl;
		setCreateDate();
	}
	
	public void setBindingName(String bindingName) {
		this.bindingName = bindingName;
	}
	
	public String getBindingName() {
		return bindingName;
	}
	
	public void setBindingUrl(String bindingUrl) {
		this.bindingUrl = bindingUrl;
	}
	
	public String getBindingUrl() {
		return bindingUrl;
	}

	public void setCreateDate() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");
		this.createDate = now.format(df);
	}
	
	@Override
    public String toString() {
        return "BindingEntity [id=" + id + ", bindingName=" + bindingName + 
                ", bindingUrl=" + bindingUrl + "]";
    }

}
