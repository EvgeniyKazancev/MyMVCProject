package ru.kazancev.project2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kazancev.project2.models.Person;
import ru.kazancev.project2.services.PeopleServices;
import ru.kazancev.project2.util.PeopleValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleServices peopleServices;
    private final PeopleValidator peopleValidator;

    @Autowired
    public PeopleController(PeopleServices peopleServices, PeopleValidator peopleValidator) {
        this.peopleServices = peopleServices;
        this.peopleValidator = peopleValidator;
    }
    @GetMapping()
    public String index(Model model){
        model.addAttribute("people",peopleServices.getAllPerson());
        return "people/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable ("id")int id, Model model){
        model.addAttribute("people",peopleServices.getPerson(id));
        model.addAttribute("books",peopleServices.getBookByPerson(id));
        return "people/show";
    }
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("people")Person person){
        return "person/new";
    }
    @PostMapping("/{id}")
    public String createPerson(@ModelAttribute("people") @Valid Person person, BindingResult bindingResult){
        peopleValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors())
            return "redirect:/new";
        else
            peopleServices.save(person);
        return "redirect:/people";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("people") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){
        if (bindingResult.hasErrors())
             return "people/edit";
            else
                peopleServices.update(id,person);
            return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable ("id") int id){
        peopleServices.delete(id);
        return "redirect:/people";
    }
    @PatchMapping("/{id}")
    public String edit(Model model,@PathVariable("id") int id){
        model.addAttribute("person",peopleServices.getPerson(id));
        return "people/edit";
    }

}
