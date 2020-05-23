package deploy.jsk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import deploy.jsk.entity.Person;
import deploy.jsk.service.PersonService;

@RestController
@RequestMapping(value = "/person")
public class PersonController {

	@Autowired
	PersonService personService;

	@GetMapping("/getAllPersons")
	List<Person> getPersonList() {

		return personService.getPersonsList();
	}

}
