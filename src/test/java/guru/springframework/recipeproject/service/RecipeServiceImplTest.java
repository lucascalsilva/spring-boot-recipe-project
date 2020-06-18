package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.commands.NotesCommand;
import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.converters.*;
import guru.springframework.recipeproject.exceptions.NotFoundException;
import guru.springframework.recipeproject.model.Recipe;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    RecipeToRecipeCommand recipeToRecipeCommand;
    CategoryToCategoryCommand categoryToCategoryCommand;
    IngredientToIngredientCommand ingredientToIngredientCommand;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    NotesToNotesCommand notesToNotesCommand;

    RecipeCommand recipeCommand;

    RecipeCommandToRecipe recipeCommandToRecipe;
    CategoryCommandToCategory categoryCommandToCategory;
    IngredientCommandToIngredient ingredientCommandToIngredient;
    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;
    NotesCommandToNotes notesCommandToNotes;


    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);

        recipeCommand = RecipeCommand.builder().id(1L).description("Some name").build();

        categoryToCategoryCommand = new CategoryToCategoryCommand();
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        ingredientToIngredientCommand = new IngredientToIngredientCommand(unitOfMeasureToUnitOfMeasureCommand);
        notesToNotesCommand = new NotesToNotesCommand();
        recipeToRecipeCommand = new RecipeToRecipeCommand(categoryToCategoryCommand, ingredientToIngredientCommand, notesToNotesCommand);

        categoryCommandToCategory = new CategoryCommandToCategory();
        unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
        ingredientCommandToIngredient = new IngredientCommandToIngredient(unitOfMeasureCommandToUnitOfMeasure);
        notesCommandToNotes = new NotesCommandToNotes();
        recipeCommandToRecipe = new RecipeCommandToRecipe(categoryCommandToCategory, ingredientCommandToIngredient,
                notesCommandToNotes);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void findByDescription() {
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);
        when(recipeRepository.findByDescriptionIgnoreCase(anyString())).thenReturn(Optional
                .of(recipe));

        RecipeCommand recipeCommandByDescription = recipeService.findByDescription("Some name");

        assertThat(recipeCommand).isEqualTo(recipeCommandByDescription);

        verify(recipeRepository).findByDescriptionIgnoreCase(anyString());
        verify(recipeRepository, never()).findById(anyLong());
    }

    @Test
    void findById() {
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);
        when(recipeRepository.findById(anyLong())).thenReturn(Optional
                .of(recipe));

        RecipeCommand recipeCommandById = recipeService.findById(1L);

        assertThat(recipeCommand).isEqualTo(recipeCommandById);

        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void findByIdNotFound() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException findByIdNotFoundException = assertThrows(NotFoundException.class, () -> recipeService.findById(1L)
                , "Expected RuntimeException when cant find recipe by ID");
        assertThat(findByIdNotFoundException.getMessage()).containsIgnoringCase("not found");

    }

    @Test
    void save() {
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);
        when(recipeRepository.save(recipe)).thenReturn(recipe);

        RecipeCommand savedRecipeCommand = recipeService.save(recipeCommand);

        verify(recipeRepository, times(1)).save(recipe);

        assertThat(savedRecipeCommand).isEqualTo(recipeCommand);
    }

    @Test
    void deleteById() {
        recipeService.deleteById(1L);

        verify(recipeRepository, times(1)).deleteById(1L);
    }

    @Test
    void findAll() {
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);
        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe));

        Set<RecipeCommand> recipeCommandsAll = recipeService.findAll();

        assertThat(recipeCommandsAll.contains(recipeCommand));
        assertThat(recipeCommandsAll.size()).isEqualTo(1);

        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository, never()).findById(anyLong());
    }
}