package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.converters.RecipeCommandToRecipe;
import guru.springframework.recipeproject.converters.RecipeToRecipeCommand;
import guru.springframework.recipeproject.model.Recipe;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import guru.springframework.recipeproject.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by jt on 6/21/17.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RecipeServiceIT {

    public static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Test
    @Transactional
    public void testSaveOfDescription() throws Exception {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        //when
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.save(testRecipeCommand);

        //then
        assertThat(NEW_DESCRIPTION).isEqualTo(savedRecipeCommand.getDescription());
        assertThat(testRecipe.getId()).isEqualTo(savedRecipeCommand.getId());
        assertThat(testRecipe.getCategories().size()).isEqualTo(savedRecipeCommand.getCategories().size());
        assertThat(testRecipe.getIngredients().size()).isEqualTo(savedRecipeCommand.getIngredients().size());
    }
}