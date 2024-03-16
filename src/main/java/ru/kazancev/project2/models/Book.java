package ru.kazancev.project2.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Book")
@Getter
@Setter
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 2, max = 100, message = "Название не должно привышать 100 символов")
    @Column(name = "title")
    private String title;

    @NotNull(message = "У книги должен быть автор")
    @Column(name = "author")
    private String author;

    @Min(value = 1500, message = "Год должен быть выше 1500")
    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private boolean expired;

    public Book() {

    }

    public Book(String title, String author, int year) {

        this.title = title;
        this.author = author;
        this.year = year;

    }
}
