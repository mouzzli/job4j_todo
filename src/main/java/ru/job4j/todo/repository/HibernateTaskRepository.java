package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {
    private static final String SET_DONE = "UPDATE Task SET done = :done WHERE id = :id";
    private static final String DELETE_BY_ID = "DELETE FROM Task WHERE id = :id";
    private static final String UPDATE = "UPDATE Task SET description = :description, done = :done WHERE id = :id";
    private static final String FIND_DONE = "FROM Task WHERE done = :done ORDER BY id";
    private static final String FIND_ALL = "FROM Task ORDER BY id";
    private final SessionFactory sessionFactory;

    @Override
    public Task create(Task task) {
        var session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public boolean update(Task task) {
        boolean result;
        var session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            result = session.createQuery(UPDATE)
                    .setParameter("description", task.getDescription())
                    .setParameter("done", task.isDone())
                    .setParameter("id", task.getId())
                    .executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean deleteById(int taskId) {
        boolean result;
        var session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            result = session.createQuery(DELETE_BY_ID)
                    .setParameter("id", taskId)
                    .executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<Task> findAllOrderByDate() {
        List<Task> result;
        var session = sessionFactory.openSession();
        session.beginTransaction();
        result = session.createQuery(FIND_ALL, Task.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Optional<Task> findById(int taskId) {
        var session = sessionFactory.openSession();
        session.beginTransaction();
        Task result = session.get(Task.class, taskId);
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(result);
    }

    @Override
    public boolean setDone(int id) {
        boolean result;
        var session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            result = session.createQuery(SET_DONE)
                    .setParameter("done", true)
                    .setParameter("id", id)
                    .executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<Task> findByStatus(boolean status) {
        List<Task> result;
        var session = sessionFactory.openSession();
        session.beginTransaction();
        result = session.createQuery(FIND_DONE, Task.class)
                .setParameter("done", status).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
