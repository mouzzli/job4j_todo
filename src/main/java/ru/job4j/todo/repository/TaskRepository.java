package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task create(Task task);

    boolean update(Task task, int userId);

    boolean deleteById(int taskId, int userId);

    List<Task> findAllOrderByDate(int userId);

    Optional<Task> findById(int taskId, int userId);

    boolean setDone(int id, int userId);

    List<Task> findByStatus(boolean status, int userId);
}
