package guru.springframework.recipeproject.repositories;

import guru.springframework.recipeproject.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    Optional<Recipe> findByDescriptionIgnoreCase(String description);

}
