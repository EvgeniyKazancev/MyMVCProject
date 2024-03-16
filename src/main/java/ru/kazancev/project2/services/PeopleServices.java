package ru.kazancev.project2.services;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import ru.kazancev.project2.models.Book;
import ru.kazancev.project2.models.Person;
import ru.kazancev.project2.repositories.PeopleRepository;

import javax.transaction.Transactional;
import java.util.*;
@Service
public class PeopleServices  {
    public  final PeopleRepository peopleRepository;

    public PeopleServices(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    public List<Person> getAllPerson(){
        return peopleRepository.findAll();
    }
    public Person getPerson(int id){
        Optional<Person> personOne = peopleRepository.findById(id);
      return   personOne.orElse(null);
    }
    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }
    @Transactional
    public void update(int id,Person updatePerson){
        updatePerson.setId(id);
        peopleRepository.save(updatePerson);
    }
    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }
    public Optional<Person> grtPersonByFulName(String firstName, String lastName) {
        return peopleRepository.findByFirstNameAndLastName(firstName, lastName);
    }
    public List<Book> getBookByPerson(int id){
        Optional<Person> person = peopleRepository.findById(id);
        if(person.isPresent()){
            Hibernate.initialize(person.get().getBookList());
            person.get().getBookList().forEach(book -> {
                      long diffMillies = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                      if (diffMillies > 864000000){
                          book.setExpired(true);
                      }
            });
            return person.get().getBookList();
        }else {
            return Collections.emptyList();
         }

    }

}






























