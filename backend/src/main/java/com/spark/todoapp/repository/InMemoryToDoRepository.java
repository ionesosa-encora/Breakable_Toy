package com.spark.todoapp.repository;

import com.spark.todoapp.model.Priority;
import com.spark.todoapp.model.ToDo;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryToDoRepository implements ToDoRepository {

    private final Map<UUID, ToDo> todos;
    private final List<UUID> order;

    public InMemoryToDoRepository() {
        this.todos = new ConcurrentHashMap<>();
        this.order = new ArrayList<>();
    }

    @Override
    public List<ToDo> getAllToDos() {
        List<ToDo> orderedToDos = new ArrayList<>();
        for (UUID id : order){
            orderedToDos.add(todos.get(id));
        }
        return Collections.unmodifiableList(orderedToDos);
    }

    @Override
    public Optional<ToDo> getToDoById(UUID id) {
        return Optional.ofNullable(todos.get(id));
    }

    @Override
    public ToDo createToDo(ToDo todo) {
        todos.put(todo.getId(), todo);
        order.add(todo.getId());
        return todo;
    }

    @Override
    public void deleteToDo(UUID id) {
        todos.remove(id);
        order.remove(id);
    }

    @Override
    public ToDo updateToDo(ToDo updatedToDo) {
        if (todos.containsKey(updatedToDo.getId())) {
            todos.put(updatedToDo.getId(), updatedToDo);
            return updatedToDo;
        }
        return null;
    }

    @Override
    public List<ToDo> findToDosByPriority(Priority priority) {
        List<ToDo> filteredToDos = new ArrayList<>();
        for (ToDo todo : todos.values()) {
            if (todo.getPriority().equals(priority)) {
                filteredToDos.add(todo);
            }
        }
        return filteredToDos;
    }

    @Override
    public List<ToDo> findToDosByStatus(boolean done) {
        List<ToDo> filteredToDos = new ArrayList<>();
        for (ToDo todo : todos.values()) {
            if (todo.isDone() == done) {
                filteredToDos.add(todo);
            }
        }
        return filteredToDos;
    }

    @Override
    public List<ToDo> findToDosByName(String name) {
        List<ToDo> filteredToDos = new ArrayList<>();
        for (ToDo todo : todos.values()) {
            if (todo.getText().toLowerCase().contains(name.toLowerCase())) {
                filteredToDos.add(todo);
            }
        }
        return filteredToDos;
    }
}