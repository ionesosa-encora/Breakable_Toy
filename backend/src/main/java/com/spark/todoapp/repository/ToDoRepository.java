package com.spark.todoapp.repository;

import com.spark.todoapp.model.Priority;
import com.spark.todoapp.model.ToDo;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ToDoRepository {
    //get all tasks
    List<ToDo> getAllToDos();

    //get task by ID
    Optional<ToDo> getToDoById(UUID id);

    //create new task
    ToDo createToDo(ToDo todo);

    //delete task by id
    void deleteToDo(UUID id);

    ToDo updateToDo(ToDo todo);

    List<ToDo> findToDosByPriority(Priority priority);

    List<ToDo> findToDosByStatus(boolean done);

    List<ToDo> findToDosByName(String name);

}
