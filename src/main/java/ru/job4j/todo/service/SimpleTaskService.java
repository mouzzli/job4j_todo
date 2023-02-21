package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.TaskRepository;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public List<Task> findAll(User user) {
        List<Task> tasks = taskRepository.findAllOrderByDate(user.getId());
        tasks.forEach(task -> setTimeZone(task, user));
        return tasks;

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
    public List<Task> findByStatus(boolean status, User user) {
        List<Task> tasks = taskRepository.findByStatus(status, user.getId());
        tasks.forEach(task -> setTimeZone(task, user));
        return tasks;
    }

    private void setTimeZone(Task task, User user) {
        ZoneId zoneId;
        if (user.getUserZone() == null) {
            zoneId = ZoneId.systemDefault();

        } else {
            zoneId = ZoneId.of(user.getUserZone());
        }
        task.setCreated(task.getCreated()
                .atZone(ZoneId.systemDefault())
                .withZoneSameInstant(zoneId)
                .toLocalDateTime());
    }
}
