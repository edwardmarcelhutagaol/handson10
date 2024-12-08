package repositories;
import entities.Todolist;
public interface TodolistRepository {
    Todolist[] getAll();
    void add(Todolist todolist);
    Boolean remove(Integer number, Boolean isNumberId);
}
