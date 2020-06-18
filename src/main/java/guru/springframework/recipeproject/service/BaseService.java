package guru.springframework.recipeproject.service;

import java.util.Optional;
import java.util.Set;

public interface BaseService<T> {

    T findById(Long id);
    T save(T object);
    void deleteById(Long id);
    Set<T> findAll();
}
