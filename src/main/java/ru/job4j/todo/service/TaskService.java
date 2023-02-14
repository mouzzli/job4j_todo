package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> findAll(int userId);

    List<Task> findByStatus(boolean status, int userId);

    Task create(Task task);

    Optional<Task> findById(int id, int userId);

    boolean setDone(int id, int userId);

    boolean deleteById(int id, int userId);

    boolean update(Task task, int id);
}
