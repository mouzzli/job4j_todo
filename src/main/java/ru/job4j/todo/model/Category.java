package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
public class Category {
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;
}
