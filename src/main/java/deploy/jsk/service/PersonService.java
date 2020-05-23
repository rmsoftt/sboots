package deploy.jsk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Multimap;

import deploy.jsk.baseInt.DataBaseEntityInt;
import deploy.jsk.common.PersonInfoCache;
import deploy.jsk.entity.Person;
import deploy.repository.PersonEntityDao;

@Service("personService")
public class PersonService {

	@Autowired
	PersonEntityDao persondao;
	@Autowired
	PersonInfoCache personInfoCache;

	public List<Person> getPersonsList() {

		List<Person> persons = new ArrayList<>();
		Multimap<String, DataBaseEntityInt> personsMultiMap = personInfoCache.getAllFilteredPersons("Y");
		persons.addAll(personsMultiMap.values().stream().map(x -> (Person) x).collect(Collectors.toList()));

		return persons;
	}
	
	
	public Person updatePerson(Person person)
	{
		
		//persondao.save(person);
		
		//persondao.
		
		
		return person;
	}

}
