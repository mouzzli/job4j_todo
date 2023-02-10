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
    public List<Task> findAll() {
        return taskRepository.findAllOrderByDate();
    }

    @Override
    public Task create(Task task) {
        return taskRepository.create(task);
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskRepository.findById(id);
    }

    @Override
    public boolean setDone(int id) {
        return taskRepository.setDone(id);
    }

    @Override
    public boolean deleteById(int id) {
        return taskRepository.deleteById(id);
    }

    @Override
    public boolean update(Task task) {
        return taskRepository.update(task);
    }

    @Override
    public List<Task> findByStatus(boolean status) {
        return taskRepository.findByStatus(status);
    }
}
