package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {
    private static final String FIND_BY_LOGIN_AND_PASSWORD = "FROM User "
            + "WHERE login = :login AND password = :password";

    private final CrudRepository crudRepository;

    @Override
    public Optional<User> save(User user) {
        try {
            crudRepository.run(session -> session.saveOrUpdate(user));
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
            return crudRepository.optional(FIND_BY_LOGIN_AND_PASSWORD,
                    User.class,
                    Map.of("login", login,
                            "password", password));
    }
}
