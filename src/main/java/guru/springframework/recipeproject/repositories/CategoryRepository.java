package guru.springframework.recipeproject.repositories;

import guru.springframework.recipeproject.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByNameIgnoreCase(String categoryName);
}
