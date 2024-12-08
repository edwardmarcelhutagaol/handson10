package services;
import entities.Todolist;
import repositories.TodolistRepository;

import java.util.Objects;


public class TodolistServiceimpl implements TodolistService {
    private final TodolistRepository todolistRepository;

    public TodolistServiceimpl(TodolistRepository todolistRepository) {
        this.todolistRepository = todolistRepository;
    }

    @Override
    public Todolist[] getTodolist() {
        return todolistRepository.getAll();
    }

    @Override
    public void addTodolist(final Todolist todolist) {
        if(Objects.isNull(todolist)){
            System.out.println("Todo list is null, please make sure todo is not null");
            return;
        }
        todolistRepository.add(todolist);
    }

    @Override
    public boolean removeTodolist(final Integer number,final Boolean isNumberId) {
        if (number < 0) {
            System.out.println("please input number correctly");
            return false;
        }
        return todolistRepository.remove(number, isNumberId);
    }
}
