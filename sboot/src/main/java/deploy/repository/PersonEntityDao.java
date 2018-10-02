package deploy.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import deploy.jsk.baseInt.DataBaseEntityInt;
import deploy.jsk.entity.Person;

public interface PersonEntityDao extends CrudRepository<Person, Long> {

	@Query(value = "select a from Person a where a.active= 'Y' and a.lastModDt> :lastModDate ")
	List<DataBaseEntityInt> findAllPersons(@Param("lastModDate") Timestamp lastModDate);

}
