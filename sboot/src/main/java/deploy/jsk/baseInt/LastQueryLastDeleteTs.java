package deploy.jsk.baseInt;

import java.sql.Date;
import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
public class LastQueryLastDeleteTs {

	private Timestamp lastQueryTs;
	private Timestamp lastDeleteTs;

	public LastQueryLastDeleteTs() {

		this.lastQueryTs = Constants.beforeJskTime;
		this.lastDeleteTs = Constants.beforeJskTime;
	}

	public LastQueryLastDeleteTs(Timestamp dt) {

		this.lastQueryTs = dt;
		this.lastDeleteTs = dt;
	}

}
