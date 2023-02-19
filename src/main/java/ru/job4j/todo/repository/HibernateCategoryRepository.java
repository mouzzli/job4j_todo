package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;

@Repository
@AllArgsConstructor
public class HibernateCategoryRepository implements CategoryRepository {
    private static final String FIND_ALL = "FROM Category";
    private final CrudRepository crudRepository;

    @Override
    public List<Category> findAll() {
        return crudRepository.query(FIND_ALL, Category.class);
    }
}
