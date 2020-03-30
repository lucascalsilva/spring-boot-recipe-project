package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Recipe findByDescription(String description);
    Recipe findById(Long id);
    Recipe save(Recipe recipe);
    void deleteById(Long id);
    Set<Recipe> findAll();

}
