package ru.job4j.todo.service;

import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> save(User user);

    Optional<User> findByLoginAndPassword(String login, String password);

    List<String> getTimeZones();
}
