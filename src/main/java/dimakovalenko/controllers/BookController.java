package dimakovalenko.controllers;

import dimakovalenko.DAO.BookDAO;
import dimakovalenko.DAO.PersonDAO;
import dimakovalenko.models.Book;
import dimakovalenko.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books",bookDAO.index());
        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model, @ModelAttribute("person")Person person){
        model.addAttribute("book",bookDAO.show(id));
        Optional<Person> bookOwner=bookDAO.getBookOwner(id);
        if(bookOwner.isPresent()){
            model.addAttribute("owner",bookOwner.get());
        }else {
            model.addAttribute("people",personDAO.index());
        }
        return "book/show";
    }

    @PostMapping
    public String create(@ModelAttribute("book")@Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "book/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }
    @PatchMapping("/{id}")
    public String update(@PathVariable int id,@ModelAttribute("book")@Valid Book book,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "book/new";
        }
        bookDAO.update(id,book);
        return "redirect:/books";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book",new Book());
        return "book/new";
    }

    @GetMapping("/{id}/edit")
     public String edit(@PathVariable int id,Model model){
        model.addAttribute("book",bookDAO.show(id));
        return "book/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable int id){
        bookDAO.release(id);
        return "redirect:/books/"+id;
    }
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id")int id,@ModelAttribute("person") Person selectedPerson){
        bookDAO.assign(id,selectedPerson);
        return "redirect:/books/"+id;
    }

}
