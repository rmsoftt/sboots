package deploy.jsk.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import deploy.jsk.baseInt.DataBaseEntity;
import deploy.jsk.utility.CustomDateDeserializer;
import deploy.jsk.utility.CustomDateSerializer;

@Entity
@Table(name = "LAST_MODIFIED", schema = "c##jsk")
@Data
public class LastModifiedEntity extends DataBaseEntity {

	@Column(name = "ID")
	@Id
	private Long lastModifiedId;

	@Basic
	@Column(name = "TABLE_NAME", length = 30, nullable = false)
	private String tableName;

	@Basic
	@Column(name = "LAST_QUERY_TS")
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private Timestamp lastQueryTs;

	@Basic
	@Column(name = "LAST_DELETE_TS")
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private Timestamp lastDeleteTs;

}
