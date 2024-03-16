package ru.kazancev.project2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kazancev.project2.models.Person;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {
   Optional<Person> findByFirstNameAndLastName(String firstName,String lastName);
}
