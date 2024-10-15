package com.spark.todoapp.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class ToDo {

    private final UUID id;
    private String text;
    private final LocalDateTime createdDate;
    private LocalDateTime dueDate;
    private boolean done;
    private LocalDateTime doneDate;
    private Priority priority;

    //constructor
    public ToDo(String text, Priority priority){
        this.id = UUID.randomUUID();
        this.text = text;
        this.priority = priority;
        this.createdDate = LocalDateTime.now();
        this.done = false;

    }
    //getters and setters
    public UUID getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        if (text.length() <= 120) {
            this.text = text;
        } else {
            throw new IllegalArgumentException("Maximum length of 120 characters");
        }
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
        if (done) {
            this.doneDate = LocalDateTime.now();
        } else {
            this.doneDate = null;
        }
    }

    public LocalDateTime getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(LocalDateTime doneDate) {
        this.doneDate = doneDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }


}
