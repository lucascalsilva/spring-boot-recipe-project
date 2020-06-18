package guru.springframework.recipeproject.converters;

import guru.springframework.recipeproject.commands.IngredientCommand;
import guru.springframework.recipeproject.model.Ingredient;
import guru.springframework.recipeproject.model.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 6/21/17.
 */
@Component
@RequiredArgsConstructor
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setQuantity(source.getQuantity());
        ingredient.setDescription(source.getDescription());
        ingredient.setUnitOfMeasure(uomConverter.convert(source.getUnitOfMeasure()));
        if(source.getRecipe() != null){
            ingredient.setRecipe(new Recipe()); ;
            ingredient.getRecipe().setId(source.getRecipe().getId());
        }
        return ingredient;
    }
}
