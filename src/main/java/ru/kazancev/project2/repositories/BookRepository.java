package ru.kazancev.project2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kazancev.project2.models.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
  List<Book> findByTitleIsStartingWith(String title);
}
