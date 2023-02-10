package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {
    private static final String FIND_BY_LOGIN_AND_PASSWORD = "FROM User WHERE login = :login AND password = :password";

    private final SessionFactory sessionFactory;

    @Override
    public Optional<User> save(User user) {
        Optional<User> optionalUser = Optional.empty();
        var session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            optionalUser = Optional.of(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        var session = sessionFactory.openSession();
        Optional<User> optionalUser;
        try {
            session.beginTransaction();
            optionalUser = session.createQuery(FIND_BY_LOGIN_AND_PASSWORD, User.class)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
        return optionalUser;
    }
}
