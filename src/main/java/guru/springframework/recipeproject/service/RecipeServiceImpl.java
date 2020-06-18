package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.converters.RecipeCommandToRecipe;
import guru.springframework.recipeproject.converters.RecipeToRecipeCommand;
import guru.springframework.recipeproject.exceptions.NotFoundException;
import guru.springframework.recipeproject.model.Recipe;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    @Override
    public RecipeCommand findByDescription(String description) {
        return recipeToRecipeCommand.convert(recipeRepository.findByDescriptionIgnoreCase(description)
                .orElseThrow(() -> new NotFoundException(Recipe.class, "description", description)));
    }

    @Override
    public RecipeCommand findById(Long id) {
        return recipeToRecipeCommand.convert(recipeRepository.findById(id).orElseThrow(() ->
                new NotFoundException(Recipe.class, "id", id.toString())));
    }

    @Override
    @Transactional
    public RecipeCommand save(RecipeCommand recipeCommand) {
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);
        return recipeToRecipeCommand.convert(recipeRepository.save(recipe));
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public Set<RecipeCommand> findAll() {
        Set<RecipeCommand> recipeCommandSet = new HashSet<RecipeCommand>();
        recipeRepository.findAll().iterator()
                .forEachRemaining(recipe ->
                        recipeCommandSet.add(recipeToRecipeCommand.convert(recipe)));
        return recipeCommandSet;

    }
}
