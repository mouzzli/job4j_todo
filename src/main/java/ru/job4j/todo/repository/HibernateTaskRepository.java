package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {
    private static final String SET_DONE = "UPDATE Task SET done = :done WHERE id = :id";
    private static final String DELETE_BY_ID = "DELETE FROM Task WHERE id = :id";
    private static final String UPDATE = "UPDATE Task SET description = :description, done = :done WHERE id = :id";
    private static final String FIND_DONE = "FROM Task WHERE done = :done ORDER BY id";
    private static final String FIND_ALL = "FROM Task ORDER BY id";
    private static final String FIND_BY_ID = "FROM Task WHERE id = :id";
    private final CrudRepository crudRepository;

    @Override
    public Task create(Task task) {
        crudRepository.run(session -> session.save(task));
        return task;
    }

    @Override
    public boolean update(Task task) {
        return crudRepository.update(UPDATE,
                Map.of("description", task.getDescription(),
                        "done", task.isDone(),
                        "id", task.getId()));
    }

    @Override
    public boolean deleteById(int taskId) {
        return crudRepository.update(DELETE_BY_ID, Map.of("id", taskId));
    }

    @Override
    public List<Task> findAllOrderByDate() {
        return crudRepository.query(FIND_ALL, Task.class);
    }

    @Override
    public Optional<Task> findById(int taskId) {
            return crudRepository.optional(FIND_BY_ID, Task.class, Map.of("id", taskId));
    }

    @Override
    public boolean setDone(int id) {
        return crudRepository.update(SET_DONE,
                Map.of(
                        "done", true,
                        "id", id));
    }

    @Override
    public List<Task> findByStatus(boolean status) {
        return crudRepository.query(FIND_DONE, Task.class, Map.of("done", status));
    }
}
