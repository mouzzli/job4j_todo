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
    private static final String SET_DONE = "UPDATE Task SET done = :done WHERE id = :id AND user_id = :userId";
    private static final String DELETE_BY_ID = "DELETE FROM Task WHERE id = :id AND user_id = :userId";
    private static final String FIND_DONE = "SELECT DISTINCT t FROM Task t JOIN FETCH t.priority JOIN FETCH t.categories WHERE done = :done AND user_id = :userId ORDER BY t.id";
    private static final String FIND_ALL = "SELECT DISTINCT t FROM Task t JOIN FETCH t.priority JOIN FETCH t.categories WHERE user_id = :userId ORDER BY t.id";
    private static final String FIND_BY_ID = "FROM Task t JOIN FETCH t.priority JOIN FETCH t.categories WHERE t.id = :id AND user_id = :userId";
    private final CrudRepository crudRepository;

    @Override
    public Task create(Task task) {
        crudRepository.run(session -> session.save(task));
        return task;
    }

    @Override
    public boolean update(Task task, int userId) {
        return crudRepository.tx(session -> {
            Task savedTask = session.get(Task.class, task.getId());
            if (savedTask != null && savedTask.getUser().getId() == userId) {
                session.merge(task);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean deleteById(int taskId, int userId) {
        return crudRepository.update(DELETE_BY_ID,
                Map.of("id", taskId,
                        "userId", userId));
    }

    @Override
    public List<Task> findAllOrderByDate(int userId) {
        return crudRepository.query(FIND_ALL, Task.class, Map.of("userId", userId));
    }

    @Override
    public Optional<Task> findById(int taskId, int userId) {
            return crudRepository.optional(FIND_BY_ID, Task.class,
                    Map.of("id", taskId,
                            "userId", userId));
    }

    @Override
    public boolean setDone(int id, int userId) {
        return crudRepository.update(SET_DONE,
                Map.of(
                        "done", true,
                        "id", id,
                        "userId", userId));
    }

    @Override
    public List<Task> findByStatus(boolean status, int userId) {
        return crudRepository.query(FIND_DONE, Task.class,
                Map.of("done", status,
                        "userId", userId));
    }
}
