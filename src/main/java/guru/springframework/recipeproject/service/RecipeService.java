package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.model.Recipe;

import java.util.Set;

public interface RecipeService extends BaseService<RecipeCommand> {

    RecipeCommand findByDescription(String description);

}
