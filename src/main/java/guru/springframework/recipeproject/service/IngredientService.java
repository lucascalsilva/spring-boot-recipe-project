package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.commands.IngredientCommand;

import java.util.Set;

public interface IngredientService extends BaseService<IngredientCommand> {

    Set<IngredientCommand> findByRecipeId(Long recipeId);

}
