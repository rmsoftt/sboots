package deploy.jsk.baseInt;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import deploy.jsk.utility.CustomDateDeserializer;
import deploy.jsk.utility.CustomDateSerializer;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class DataBaseEntity implements DataBaseEntityInt {

	//@JsonIgnore
	//public String id;

	@Basic
	@Column(name = "ACTIVE", length = 1)
	private String active;

	@Basic
	@Column(name = "LAST_MOD_BY", length = 20)
	private String lastModBy;

	@Basic
	@Column(name = "LAST_MOD_DT")
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private Date lastModDt;

	@Basic
	@Column(name = "CREATED_BY", length = 20)
	private String createdBy;

	@Basic
	@Column(name = "CREATED_DT")
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private Date createdDt;

	@Override
	public String getId() {
		return "0";
	}
}
