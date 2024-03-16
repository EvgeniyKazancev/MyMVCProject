package ru.kazancev.project2.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Person")
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Size(min = 1900, max = 2024)
    @Column(name = "year_birth")
    private int yearBirth;

    @OneToMany(mappedBy = "owner")
    private List<Book> bookList;

    public Person(String lastName,String firstName,int yearBirth) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.yearBirth = yearBirth;
    }
    public Person() {

    }
}
