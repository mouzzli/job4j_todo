package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.List;
import java.util.NoSuchElementException;

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
    public Task findById(int id) {
        var optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new NoSuchElementException(String.format("Задачи с id = %s не сушествует", id));
        }
        return optionalTask.get();
    }

    @Override
    public void setDone(int id) {
        if (!taskRepository.setDone(id)) {
            throw new NoSuchElementException(String.format("Невозможно завершить задачу. id = %s не сушествует", id));
        }
    }

    @Override
    public void deleteById(int id) {
        if (!taskRepository.deleteById(id)) {
            throw new NoSuchElementException(String.format("Невозможно удалить задачу. id = %s не существует", id));
        }
    }

    @Override
    public void update(Task task) {
        if (!taskRepository.update(task)) {
            throw new NoSuchElementException(String.format("Невозможно обновить задачу. id = %s не существует", task.getId()));
        }
    }

    @Override
    public List<Task> findByStatus(boolean status) {
        return taskRepository.findByStatus(status);
    }
}
