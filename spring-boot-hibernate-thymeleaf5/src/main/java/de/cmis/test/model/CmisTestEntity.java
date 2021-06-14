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
@Table(name="TBL_CMISTESTS")
@Getter @Setter @NoArgsConstructor @ToString
public class CmisTestEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name="Test_Name")
    private String testName;
    
    @Column(name="Test_Kategorie")
    private String testKategorie;
    
    @Column(name="Test_Ergebnis")
    private String testErgebnis;
    
    @Column(name="Test_Datum")
    private String testDatum;
    
    public void setTestName (String testName) {
    	this.testName = testName;
    }
    
    public void setTestKategorie (String testKategorie) {
    	this.testKategorie = testKategorie;
    }
	
    public void setTestErgebnis (String testErgebnis) {
    	this.testErgebnis = testErgebnis;
    }
    
    public void setTestDatum () {
    	LocalDateTime now = LocalDateTime.now();
    	DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");
    	this.testDatum = now.format(df);
    }
    
}
