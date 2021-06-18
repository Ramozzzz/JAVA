package com.example.MyRest.controllers;

import com.example.MyRest.entities.Person;
import com.example.MyRest.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(value = "/person")
    public ResponseEntity<?> create(@RequestBody Person person) {
        personService.create(person);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/person")
    public ResponseEntity<List<Person>> read() {
        final List<Person> persons = personService.readAll();

        return persons != null &&  !persons.isEmpty()
                ? new ResponseEntity<>(persons, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/person/{personId}")
    public ResponseEntity<Person> read(@PathVariable(name = "personId") long personId) {
        final Person person = personService.read(personId);

        return person != null
                ? new ResponseEntity<>(person, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/person/{personId}")
    public ResponseEntity<?> update(@PathVariable(name = "personId") long personId, @RequestBody Person person) {
        final boolean updated = personService.update(person, personId);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/person/{personId}")
    public ResponseEntity<?> delete(@PathVariable(name = "personId") long personId) {
        final boolean deleted = personService.delete(personId);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
