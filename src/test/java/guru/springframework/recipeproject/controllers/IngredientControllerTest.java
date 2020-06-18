package guru.springframework.recipeproject.controllers;

import guru.springframework.recipeproject.commands.IngredientCommand;
import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.commands.UnitOfMeasureCommand;
import guru.springframework.recipeproject.service.IngredientService;
import guru.springframework.recipeproject.service.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class IngredientControllerTest {

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    @InjectMocks
    IngredientController ingredientController;

    IngredientCommand ingredientCommand;

    MockMvc mockMvc;

    @BeforeEach
    public void init(){
        RecipeCommand recipeCommand = RecipeCommand.builder().id(1L).description("Some recipe").build();
        ingredientCommand = IngredientCommand.builder().id(1L).description("Some ingredient").quantity(new BigDecimal(5))
                .unitOfMeasure(UnitOfMeasureCommand.builder().id(1L).description("Unit").build()).recipe(recipeCommand).build();

        ingredientController = new IngredientController(ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    //Got to test update, new and save or update
    @Test
    public void testGetNewIngredientForm() throws Exception {
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<UnitOfMeasureCommand>();
        unitOfMeasureCommands.add(ingredientCommand.getUnitOfMeasure());

        when(unitOfMeasureService.findAll()).thenReturn(unitOfMeasureCommands);

        mockMvc.perform(get("/recipes/1/ingredients/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredient_merge"))
                .andExpect(model().attributeExists("unitOfMeasures"))
                .andExpect(model().attributeExists("ingredient"));

        verify(unitOfMeasureService, times(1)).findAll();

    }

    @Test
    public void testGetUpdateIngredientForm() throws Exception {
        when(ingredientService.findById(1L)).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipes/1/ingredients/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredient_merge"))
                .andExpect(model().attributeExists("unitOfMeasures"))
                .andExpect(model().attribute("ingredient", ingredientCommand));

        verify(ingredientService, times(1)).findById(1L);
    }

    @Test
    public void testDeleteIngredientForm() throws Exception {
        mockMvc.perform(get("/recipes/1/ingredients/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes/1/ingredients"));

        verify(ingredientService, times(1)).deleteById(1L);
    }

    @Test
    public void testSaveOrUpdateIngredient() throws Exception{
        when(ingredientService.save(any(IngredientCommand.class))).thenReturn(ingredientCommand);

        mockMvc.perform(post("/recipes/1/ingredients")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "Some ingredient"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes/" + ingredientCommand.getRecipe().getId() + "/ingredients"));

        verify(ingredientService, times(1)).save(any(IngredientCommand.class));
    }

    @Test
    public void testGetIngredientByIdForm() throws Exception {
        when(ingredientService.findById(anyLong())).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipes/1/ingredients/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredient_detail"))
                .andExpect(model().attribute("ingredient", ingredientCommand));

        verify(ingredientService).findById(1L);

    }

    @Test
    public void testGetAllByRecipeIdIngredientsForm() throws Exception {
        Set<IngredientCommand> ingredientCommandSet = new HashSet<>();

        IntStream.range(0, 2).forEach(value -> {
            RecipeCommand recipeCommand = RecipeCommand.builder().description(UUID.randomUUID().toString()).build();
            IngredientCommand ingredientCommand = IngredientCommand.builder().description(UUID.randomUUID().toString()).build();
            ingredientCommandSet.add(ingredientCommand);
        });

        when(ingredientService.findByRecipeId(1L)).thenReturn(ingredientCommandSet);

        mockMvc.perform(get("/recipes/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredient_list"))
                .andExpect(model().attribute("ingredients", ingredientCommandSet));

        verify(ingredientService, times(1)).findByRecipeId(1L);
    }
}
