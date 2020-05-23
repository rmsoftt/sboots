package deploy.jsk.common;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Multimap;

import deploy.jsk.baseInt.DataBaseEntityInt;
import deploy.jsk.baseInt.LastQueryLastDeleteTs;
import deploy.jsk.entity.Person;
import deploy.jsk.utility.MultimapCollector;
import deploy.repository.PersonEntityDao;

@Component
public class PersonInfoCache extends BaseInfoCache implements PersonInfoCacheInt {

	@Autowired
	private PersonEntityDao personEntityDao;

	public Multimap<String, DataBaseEntityInt> getAllFilteredPersons(String dataFilter, Boolean... useCache) {

		if (useCache != null && useCache.length != 0 && useCache[0]) {
			return personCache.get(dataFilter);

		}

		Timestamp time = canUseCache(dataFilter, personCache, Person.class.getAnnotation(Table.class).name(),
				personQueryTimeCache);

		if (time == null)
			return personCache.get(dataFilter);

		List<DataBaseEntityInt> results = personEntityDao.findAllPersons(time);

		if (personCache.size() == 0 || !personCache.containsKey(dataFilter)) {
			Multimap<String, DataBaseEntityInt> allPersons = results.stream()
					.collect(MultimapCollector.toMultimap(DataBaseEntityInt::getId, x -> x));
			personCache.put(dataFilter, allPersons);
			personQueryTimeCache.put(dataFilter,
					new LastQueryLastDeleteTs(new Timestamp(System.currentTimeMillis() - 1000)));
			return allPersons;
		}

		Multimap<String, DataBaseEntityInt> allPersons = super.updateCache(dataFilter, results, 
				personCache,Person.class.getName());

		
		personQueryTimeCache.put(dataFilter,
				new LastQueryLastDeleteTs(new Timestamp(System.currentTimeMillis() - 1000)));

		return personCache.get(dataFilter);
	}

}
