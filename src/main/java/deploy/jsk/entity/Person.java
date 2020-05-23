package deploy.jsk.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import deploy.jsk.baseInt.DataBaseEntity;

@Entity
@Table(name = "person", schema = "jsk")
@Data
@EqualsAndHashCode(callSuper = true)
public class Person extends DataBaseEntity {

	@Id
	@Column(name = "PERSON_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSON_ENT_SEQ")
	@SequenceGenerator(name = "PERSON_ENT_SEQ", sequenceName = "c##jsk.person_seq")
	Long personId;

	@Override
	public String getId() {
		return String.valueOf(personId);
	}

	@Basic
	@Column(name = "FIRST_NAME", nullable = false, length = 20)
	private String firstName;

	@Basic
	@Column(name = "MIDDLE_NAME", length = 20)
	private String middleName;

	@Basic
	@Column(name = "LAST_NAME", length = 20)
	private String lastName;

	@Column(name = "AGE")
	@Basic
	private Integer age;
	
	@OneToOne(mappedBy="person")
	PassportDetails passportDetails;

}
