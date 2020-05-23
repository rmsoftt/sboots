package deploy.jsk.baseInt;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.stereotype.Component;

//@Singleton
@Component
public class Constants {

	static public Timestamp beforeJskTime;
	static {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2017, 01, 01);
		beforeJskTime = new Timestamp(calendar.getTime().getTime());

	}

}
