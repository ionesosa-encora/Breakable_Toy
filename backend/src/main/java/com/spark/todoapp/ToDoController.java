package com.spark.todoapp;

import com.spark.todoapp.model.ToDo;
import com.spark.todoapp.model.Priority;
import com.spark.todoapp.model.ToDoPaginationResponse;
import com.spark.todoapp.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Map;


@RestController
@RequestMapping("/todos")
public class ToDoController {
    private final ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoService toDoService){
        this.toDoService = toDoService;
    }

    @GetMapping
    public ToDoPaginationResponse getAllToDos(@RequestParam(defaultValue = "0") int page){
        return toDoService.getAllToDosWithPagination(page);
    }

    @GetMapping("/{id}")
    public Optional<ToDo> getToDoById(@PathVariable UUID id){
        return toDoService.getToDoById(id);
    }

    @PostMapping
    public ToDo createToDo(@RequestBody ToDo newToDo){
        return toDoService.createToDo(newToDo.getText(), newToDo.getPriority(), newToDo.getDueDate());
    }

    @PutMapping("/{id}")
    public ToDo updateToDo(@PathVariable UUID id, @RequestBody ToDo updatedToDo) {
        String dueDateString = (updatedToDo.getDueDate() != null) ? updatedToDo.getDueDate().toString() : null;
        return toDoService.updateToDo(id, updatedToDo.getText(), updatedToDo.getPriority(), dueDateString);
    }

    @DeleteMapping("/{id}")
    public void deleteToDo(@PathVariable UUID id){
        toDoService.deleteToDo(id);
    }

    @GetMapping("/filter")
    public ToDoPaginationResponse findToDos(
            @RequestParam(required = false) Boolean done,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Priority priority,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String sortByPriority,
            @RequestParam(required = false) String sortByDueDate
    ) {
        return toDoService.findToDos(name, done, priority, page, sortByPriority, sortByDueDate);
    }

    @PostMapping("/{id}/done")
    public ToDo markAsDone(@PathVariable UUID id){
        return toDoService.markAsDone(id);
    }

    @PutMapping("/{id}/undone")
    public ToDo markAsUndone(@PathVariable UUID id){
        return toDoService.markAsUndone(id);
    }

    @GetMapping("/average-completion-time")
    public Map<String, String> getAverageCompletionTime(){
        return toDoService.calculateAverageCompletionTime();
    }

}
