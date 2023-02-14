package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public List<Task> findAll(int userId) {
        return taskRepository.findAllOrderByDate(userId);
    }

    @Override
    public Task create(Task task) {
        return taskRepository.create(task);
    }

    @Override
    public Optional<Task> findById(int id, int userId) {
        return taskRepository.findById(id, userId);
    }

    @Override
    public boolean setDone(int id, int userId) {
        return taskRepository.setDone(id, userId);
    }

    @Override
    public boolean deleteById(int id, int userId) {
        return taskRepository.deleteById(id, userId);
    }

    @Override
    public boolean update(Task task, int userId) {
        return taskRepository.update(task, userId);
    }

    @Override
    public List<Task> findByStatus(boolean status, int userId) {
        return taskRepository.findByStatus(status, userId);
    }
}
