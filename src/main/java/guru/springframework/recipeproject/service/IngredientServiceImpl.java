package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.commands.IngredientCommand;
import guru.springframework.recipeproject.converters.IngredientCommandToIngredient;
import guru.springframework.recipeproject.converters.IngredientToIngredientCommand;
import guru.springframework.recipeproject.exceptions.NotFoundException;
import guru.springframework.recipeproject.model.Ingredient;
import guru.springframework.recipeproject.repositories.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Override
    public IngredientCommand findById(Long id) {
        return ingredientToIngredientCommand.convert(ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Ingredient.class, "id", id.toString())));
    }

    @Override
    public IngredientCommand save(IngredientCommand object) {
        Ingredient savedIngredient = ingredientRepository.save(ingredientCommandToIngredient.convert(object));
        return ingredientToIngredientCommand.convert(savedIngredient);
    }

    @Override
    public void deleteById(Long id) {
        ingredientRepository.deleteById(id);
    }

    @Override
    public Set<IngredientCommand> findAll() {
        Set<IngredientCommand> ingredients = new HashSet<>();
        ingredientRepository.findAll().iterator().forEachRemaining(ingredient -> {
            ingredients.add(ingredientToIngredientCommand.convert(ingredient));
        });

        return ingredients;
    }

    @Override
    public Set<IngredientCommand> findByRecipeId(Long recipeId) {
        Set<IngredientCommand> ingredients = new HashSet<>();
        ingredientRepository.findByRecipeId(recipeId).iterator().forEachRemaining(ingredient -> {
            ingredients.add(ingredientToIngredientCommand.convert(ingredient));
        });
        return ingredients;
    }
}
