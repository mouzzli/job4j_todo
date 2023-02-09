package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAll();

    List<Task> findByStatus(boolean status);

    Task create(Task task);

    Task findById(int id);

    void setDone(int id);

    void deleteById(int id);

    void update(Task task);
}
