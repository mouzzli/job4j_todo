package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> findAll();

    List<Task> findByStatus(boolean status);

    Task create(Task task);

    Optional<Task> findById(int id);

    boolean setDone(int id);

    boolean deleteById(int id);

    boolean update(Task task);
}
