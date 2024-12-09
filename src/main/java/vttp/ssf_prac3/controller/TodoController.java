package vttp.ssf_prac3.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import vttp.ssf_prac3.model.Todo;
import vttp.ssf_prac3.service.TodoService;
import vttp.ssf_prac3.util.Util;

@Controller
public class TodoController {
    
    @Autowired
    TodoService todoService;

    @GetMapping("/")
    public String listingPage(Model model) throws ParseException{

        List<Todo> todoList = todoService.getAllTodo();
        model.addAttribute("todoList", todoList);
        model.addAttribute("statuses", Util.statusOption);

        return "listing";
    }

    @PostMapping("/")
    public String filteredListingPage(@RequestParam(required = false) List<String> statuses, Model model) throws ParseException{
        
        model.addAttribute("statuses", Util.statusOption);
        if (statuses!=null){
            List<Todo> filteredTodoList = todoService.getFilteredTodo(statuses);
            model.addAttribute("todoList", filteredTodoList);
            return "listing";
        }
        
        return "redirect:/";
    }

    @GetMapping("/add")
    public String todoForm(Model model){
        model.addAttribute("statusOptions", Util.statusOption);
        model.addAttribute("priorityOptions", Util.priorityOption);
        model.addAttribute("todo", new Todo());
        return "form";
    }

    @PostMapping("/add")
    public String createEntry(@Valid @ModelAttribute Todo todo, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            System.out.println("error");
            System.out.println(todo.toString());
            return "form";
        }

        String id = todo.getId();
        String entry = todo.toString();
        System.out.println(entry);
        todoService.createEntry(id, entry);

        return "redirect:/";
    }
}
