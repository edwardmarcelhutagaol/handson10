package services;

import entities.Todolist;

public interface TodolistService {
    Todolist[] getTodolist();
    void addTodolist(Todolist todolist);
    boolean removeTodolist(Integer number, Boolean isNumberId);
}
