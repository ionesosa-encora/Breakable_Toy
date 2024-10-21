package com.spark.todoapp.model;

import java.util.List;

public class ToDoPaginationResponse {
    private List<ToDo> todos;
    private int totalItems;

    // Constructor
    public ToDoPaginationResponse(List<ToDo> todos, int totalItems) {
        this.todos = todos;
        this.totalItems = totalItems;
    }

    // Getters y setters
    public List<ToDo> getTodos() {
        return todos;
    }

    public void setTodos(List<ToDo> todos) {
        this.todos = todos;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}