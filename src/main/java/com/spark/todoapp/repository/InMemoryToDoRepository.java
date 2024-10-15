package com.spark.todoapp.repository;
import com.spark.todoapp.model.Priority;
import com.spark.todoapp.model.ToDo;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public class InMemoryToDoRepository implements ToDoRepository {

    private final List<ToDo> todos = new ArrayList<>();

    @Override
    public List<ToDo> getAllToDos(){
        return todos;
    }

    @Override
    public Optional<ToDo> getToDoById(UUID id){
        return todos.stream().filter(todo -> todo.getId().equals(id)).findFirst();
    }

    @Override
    public ToDo createToDo(ToDo todo){
        todos.add(todo);
        return todo;
    }

    @Override
    public void deleteToDo(UUID id){
        todos.removeIf(todo -> todo.getId().equals(id));
    }

    @Override
    public ToDo updateToDo(ToDo updatedToDo){
        Optional<ToDo> existingToDoOpt = getToDoById(updatedToDo.getId());
        if (existingToDoOpt.isPresent()){
            ToDo existingToDo = existingToDoOpt.get();

            existingToDo.setText(updatedToDo.getText());
            existingToDo.setPriority(updatedToDo.getPriority());
            existingToDo.setDueDate(updatedToDo.getDueDate());

            if (!existingToDo.isDone() && updatedToDo.isDone()){
                existingToDo.setDone(true);
                existingToDo.setDoneDate(LocalDateTime.now());

            }else if (existingToDo.isDone() && !updatedToDo.isDone()){
                existingToDo.setDone(false);
                existingToDo.setDoneDate(null);
            }

            return existingToDo;
        }
        return null;
    }

    @Override
    public List<ToDo> findToDosByPriority(Priority priority){
        List<ToDo> filteredToDos = new ArrayList<>();
        for (ToDo todo : todos){
            if (todo.getPriority().equals(priority)){
                filteredToDos.add(todo);
            }
        }
        return filteredToDos;
    }

    @Override
    public List<ToDo> findToDosByStatus(boolean done){
        List<ToDo> filteredToDos = new ArrayList<>();
        for (ToDo todo : todos){
            if (todo.isDone() == done){
                filteredToDos.add(todo);
            }
        }
        return filteredToDos;
    }

    @Override
    public List<ToDo> findToDosByName(String name){
        List<ToDo> filteredToDos = new ArrayList<>();
        for (ToDo todo : todos){
            if (todo.getText().toLowerCase().contains(name.toLowerCase())){
                filteredToDos.add(todo);
            }
        }
        return filteredToDos;
    }


}
