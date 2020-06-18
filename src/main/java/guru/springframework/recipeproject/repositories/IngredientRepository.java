package guru.springframework.recipeproject.repositories;

import guru.springframework.recipeproject.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

    Iterable<Ingredient> findByRecipeId(Long id);
}
