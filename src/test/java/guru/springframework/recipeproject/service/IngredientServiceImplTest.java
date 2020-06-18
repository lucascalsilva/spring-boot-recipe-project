package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.commands.IngredientCommand;
import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.commands.UnitOfMeasureCommand;
import guru.springframework.recipeproject.converters.*;
import guru.springframework.recipeproject.model.Ingredient;
import guru.springframework.recipeproject.model.Recipe;
import guru.springframework.recipeproject.repositories.IngredientRepository;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.in;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    IngredientServiceImpl ingredientService;

    @Mock
    IngredientRepository ingredientRepository;

    IngredientToIngredientCommand ingredientToIngredientCommand;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    IngredientCommandToIngredient ingredientCommandToIngredient;
    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    IngredientCommand ingredientCommand;

    @BeforeEach
    public void init(){
        ingredientCommand = IngredientCommand.builder().id(1L).description("Some name")
                .unitOfMeasure(UnitOfMeasureCommand.builder().id(1L).description("Unit").build()).build();

        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        ingredientToIngredientCommand = new IngredientToIngredientCommand(unitOfMeasureToUnitOfMeasureCommand);

        unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
        ingredientCommandToIngredient = new IngredientCommandToIngredient(unitOfMeasureCommandToUnitOfMeasure);
        ingredientService = new IngredientServiceImpl(ingredientRepository, ingredientToIngredientCommand, ingredientCommandToIngredient);

    }

    @Test
    void findById() {
        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);

        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(ingredient));

        IngredientCommand ingredientCommandById = ingredientService.findById(1L);

        assertThat(ingredientCommand).isEqualTo(ingredientCommandById);

        verify(ingredientRepository).findById(anyLong());
        verify(ingredientRepository, never()).findAll();
    }

    @Test
    void findByIdNotFound() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException findByIdNotFoundException = assertThrows(RuntimeException.class, () -> ingredientService.findById(1L)
                , "Expected RuntimeException when cant find recipe by ID");
        assertThat(findByIdNotFoundException.getMessage()).containsIgnoringCase("not found");

    }

    @Test
    void save() {
        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
        doAnswer(returnsFirstArg()).when(ingredientRepository).save(any(Ingredient.class));

        IngredientCommand savedIngredientCommand = ingredientService.save(ingredientCommand);

        verify(ingredientRepository, times(1)).save(ingredient);

        assertThat(savedIngredientCommand).isEqualTo(ingredientCommand);
    }

    @Test
    void deleteById() {
        ingredientService.deleteById(1L);

        verify(ingredientRepository, times(1)).deleteById(1L);
    }

    @Test
    void findAll() {
        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
        when(ingredientRepository.findAll()).thenReturn(Arrays.asList(ingredient));

        Set<IngredientCommand> ingredientCommandsAll = ingredientService.findAll();

        assertThat(ingredientCommandsAll.contains(ingredientCommand));
        assertThat(ingredientCommandsAll.size()).isEqualTo(1);

        verify(ingredientRepository, times(1)).findAll();
        verify(ingredientRepository, never()).findById(anyLong());
    }
}