package ru.kazancev.project2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kazancev.project2.models.Book;
import ru.kazancev.project2.models.Person;
import ru.kazancev.project2.services.BookServices;
import ru.kazancev.project2.services.PeopleServices;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookServices bookServices;
    private final PeopleServices peopleServices;

    @Autowired
    public BooksController(BookServices bookServices, PeopleServices peopleServices) {
        this.bookServices = bookServices;
        this.peopleServices = peopleServices;
    }

    @GetMapping("/index")
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "booksPerPage", required = false) Integer booksPerPage,
                        @RequestParam(value = "sortByYear", required = false) boolean sortByYear) {
        if (page == null || booksPerPage == null)
            model.addAttribute("books", bookServices.findAll(sortByYear));
        else
            model.addAttribute("books", bookServices.findWithPagination(page, booksPerPage, sortByYear));

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @RequestParam("id") int id, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookServices.getBook(id));
        Person bookOwner = bookServices.getOwner(id);
        if (bookOwner != null)
            model.addAttribute("owner", bookOwner);
        else
            model.addAttribute("people", peopleServices.getAllPerson());
        return "books/show";
    }

    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";
        else
            bookServices.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                             @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";
        else
            bookServices.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookServices.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookServices.release(id);
        return "redirect:/books/" + id;
    }
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson){
        bookServices.assign(id,selectedPerson);
        return "redirect:/books/" + id;
    }

    @GetMapping("/{title}")
    public String bookTitle(@RequestParam(value = "titel") String title) {
        return "";
    }
    @GetMapping("/search")
    public String searchPage() {
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("books",bookServices.findBookTitle(query));
        return "books/search";
    }

}
