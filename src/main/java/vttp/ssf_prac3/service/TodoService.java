package vttp.ssf_prac3.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.ssf_prac3.model.Todo;
import vttp.ssf_prac3.repo.TodoRepo;

@Service
public class TodoService {
    
    @Autowired
    TodoRepo todoRepo;

    public List<Todo> getAllTodo() throws ParseException{
        Set<String> keys = todoRepo.getKeys();
        List<Todo> todoList = new ArrayList<>();
        for (String key : keys){
            Todo todo = todoRepo.getById(key);
            todoList.add(todo);
        }
        return todoList;
    }

    public List<Todo> getFilteredTodo(List<String> statuses) throws ParseException{
        
        List<Todo> filteredTodoList = new ArrayList<>();
        List<Todo> allTodo = getAllTodo();

        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("Pending", "pending");
        statusMap.put("In Progress", "in_progress");
        statusMap.put("Completed", "completed");

        for (String s : statuses){
            String status = statusMap.get(s);
            List<Todo> tempList = allTodo.stream().filter(t -> t.getStatus().equals(status)).toList();
            filteredTodoList.addAll(tempList);
        }
        return filteredTodoList;
    }

    public void createEntry(String id, String entry){
        todoRepo.create(id, entry);
    }
}
