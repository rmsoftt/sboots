package deploy.jsk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import deploy.jsk.baseInt.DataBaseEntity;

@Entity
@Table(name = "PASSPORT_DETAILS", schema = "c##jsk")
@Data
@EqualsAndHashCode(callSuper = true)
public class PassportDetails extends DataBaseEntity {
	
	@Id
	@GeneratedValue(generator="pass_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="pass_seq",schema="c##jsk",sequenceName="PASSPORT_DETAILS_SEQ")
	@Column(name="PASSPORT_DETAILS_ID",nullable=false,unique=true)
	Long passportDetailsId;
	
	@Column(name="PASSPORT_NO",nullable=false)
	String passportNo;
	
	@Column(name="ADDRESS1",length=20,nullable=false)
	String address1;
	
	@Column(name="ADDRESS2",length=20)
	String address2;
	
	@Column(name="STATE_CD",length=20)
	String stateCd;
	
	@Column(name="ZIP_CD",length=20)
	String zipCd;
	
	@OneToOne()
	@JoinColumn(name="PERSON_ID",referencedColumnName="PERSON_ID")
	Person person;
	
	
	

}
