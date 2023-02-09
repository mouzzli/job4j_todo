package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task create(Task task);

    boolean update(Task task);

    boolean deleteById(int taskId);

    List<Task> findAllOrderByDate();

    Optional<Task> findById(int taskId);

    boolean setDone(int id);

    List<Task> findByStatus(boolean status);
}
