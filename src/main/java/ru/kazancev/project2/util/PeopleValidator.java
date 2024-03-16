package ru.kazancev.project2.util;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kazancev.project2.models.Person;
import ru.kazancev.project2.services.PeopleServices;

public class PeopleValidator implements Validator {
    private final PeopleServices peopleServices;

    public PeopleValidator(PeopleServices peopleServices) {
        this.peopleServices = peopleServices;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (peopleServices.grtPersonByFulName(person.getFirstName(),person.getLastName()).isPresent())
            errors.rejectValue("fullName", "", "Человек с таким ФИО уже существует");
    }
}
