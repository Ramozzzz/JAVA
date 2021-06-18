package com.example.MyRest.services;

import com.example.MyRest.entities.Person;
import com.example.MyRest.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> readAll(){
        return personRepository.findAll();
    }

    public void create(Person person){
        personRepository.save(person);
    }

    public Person read(long personId) {
        return personRepository.getOne(personId);
    }

    public boolean update(Person person, long personId) {
        if (personRepository.existsById(personId)) {
            person.setPersonId(personId);
            personRepository.save(person);
            return true;
        }

        return false;
    }

    public boolean delete(long personId) {
        if (personRepository.existsById(personId)) {
            personRepository.deleteById(personId);
            return true;
        }
        return false;
    }
}
