CREATE TABLE tasks_to_categories
(
    task_id     INT NOT NULL REFERENCES tasks (id),
    category_id INT NOT NULL REFERENCES categories (id)
);
