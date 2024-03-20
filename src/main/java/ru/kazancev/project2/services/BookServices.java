package ru.kazancev.project2.services;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.kazancev.project2.models.Book;
import ru.kazancev.project2.models.Person;
import ru.kazancev.project2.repositories.BookRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class BookServices {
    private final BookRepository bookRepository;

    public BookServices(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear)
            return bookRepository.findAll(Sort.by("year"));
        else
            return bookRepository.findAll();

    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (sortByYear)
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        else
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Book getBook(int id) {
        Optional<Book> findBook = bookRepository.findById(id);
        return findBook.orElse(null);
    }

    public List<Book> findBookTitle(String title) {
        return bookRepository.findByTitleIsStartingWith(title);

    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    public void delete(int id) {
        bookRepository.deleteById(id);

    }

    @Transactional
    public void update(int id, Book updateBook) {
        Book bookUpdate = bookRepository.findById(id).get();
        bookUpdate.setId(id);
        bookUpdate.setOwner(updateBook.getOwner());
        bookRepository.save(bookUpdate);
    }

    public Person getOwner(int id) {
        return bookRepository.findById(id).map(Book::getOwner).orElse(null);

    }

    @Transactional
    public void release(int id) {
        bookRepository.findById(id).ifPresent(book ->
        {
            book.setOwner(null);
            book.setTakenAt(null); // текущее время
        });
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setOwner(selectedPerson);
            book.setTakenAt(new Date());
        });
    }

}









































