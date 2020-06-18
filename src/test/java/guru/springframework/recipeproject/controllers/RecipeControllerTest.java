package guru.springframework.recipeproject.controllers;

import guru.springframework.recipeproject.commands.CategoryCommand;
import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.exceptions.NotFoundException;
import guru.springframework.recipeproject.model.Recipe;
import guru.springframework.recipeproject.service.CategoryService;
import guru.springframework.recipeproject.service.RecipeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.internal.util.collections.Sets;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    CategoryService categoryService;

    @Mock
    Model model;

    @InjectMocks
    RecipeController recipeController;

    RecipeCommand recipeCommand;

    MockMvc mockMvc;

    @BeforeEach
    public void init() {
        recipeCommand = RecipeCommand.builder().id(1L).description("Some recipe").build();
        recipeController = new RecipeController(recipeService, categoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    //Got to test update, new and save or update
    @Test
    public void testGetNewRecipeForm() throws Exception {
        CategoryCommand categoryCommand = CategoryCommand.builder().id(1L).name("Category 1").build();
        Set<CategoryCommand> categories = Sets.newSet(categoryCommand);

        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(get("/recipes/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipe_merge"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attributeExists("categories"));

        verify(categoryService, times(1)).findAll();

    }

    @Test
    public void testGetUpdateRecipeForm() throws Exception {
        CategoryCommand categoryCommand = CategoryCommand.builder().id(1L).name("Category 1").build();
        Set<CategoryCommand> categories = Sets.newSet(categoryCommand);

        when(categoryService.findAll()).thenReturn(categories);
        when(recipeService.findById(1L)).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipes/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipe_merge"))
                .andExpect(model().attribute("recipe", recipeCommand))
                .andExpect(model().attributeExists("categories"));

        verify(recipeService, times(1)).findById(1L);
        verify(categoryService, times(1)).findAll();
    }

    @Test
    public void testDeleteRecipeForm() throws Exception {
        mockMvc.perform(get("/recipes/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes"));

        verify(recipeService, times(1)).deleteById(1L);
    }

    @Test
    public void testSaveOrUpdateRecipe() throws Exception {
        when(recipeService.save(any(RecipeCommand.class))).thenReturn(recipeCommand);

        mockMvc.perform(post("/recipes")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "Some description")
                .param("directions", "Some directions"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes/" + recipeCommand.getId() + "/update"));

        verify(recipeService, times(1)).save(any(RecipeCommand.class));
    }

    @Test
    public void testSaveOrUpdateRecipeValidationFail() throws Exception {
        mockMvc.perform(post("/recipes")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipe_merge"));

        verifyNoInteractions(recipeService);
    }

    @Test
    public void testGetRecipeByIdForm() throws Exception {
        when(recipeService.findById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipes/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipe_detail"))
                .andExpect(model().attribute("recipe", recipeCommand));

        verify(recipeService).findById(1L);
    }

    @Test
    public void testGetRecipeByIdNotFound() throws Exception {
        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipes/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error_page"))
                .andExpect(model().attribute("exception", Matchers.any(NotFoundException.class)))
                .andExpect(model().attribute("httpStatus", Matchers.equalTo(HttpStatus.NOT_FOUND)));

        verify(recipeService).findById(1L);
    }

    @Test
    public void testGetRecipeByIdFormatError() throws Exception {
        mockMvc.perform(get("/recipes/asdas/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error_page"))
                .andExpect(model().attribute("exception", Matchers.any(NumberFormatException.class)))
                .andExpect(model().attribute("httpStatus", Matchers.equalTo(HttpStatus.BAD_REQUEST)));
    }

    @Test
    public void testGetAllRecipesForm() throws Exception {
        Set<RecipeCommand> recipeCommandSet = new HashSet<RecipeCommand>();

        IntStream.range(0, 2).forEach(value -> {
            RecipeCommand recipe = RecipeCommand.builder().description(UUID.randomUUID().toString()).build();
            recipeCommandSet.add(recipe);
        });

        when(recipeService.findAll()).thenReturn(recipeCommandSet);

        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipe_list"))
                .andExpect(model().attribute("recipes", recipeCommandSet));

        verify(recipeService, times(1)).findAll();
    }

    @Test
    public void testGetRecipeByIdNoMockMvc() {
        when(recipeService.findById(anyLong())).thenReturn(recipeCommand);

        String page = recipeController.getRecipeById(model, "1");

        assertThat(page).isEqualTo("recipe/recipe_detail");
        verify(model).addAttribute(eq("recipe"), eq(recipeCommand));
        verify(recipeService).findById(1L);
    }

    @Test
    public void testGetAllRecipesNoMockMvc() {
        Set<RecipeCommand> recipeCommandSet = new HashSet<RecipeCommand>();

        IntStream.range(0, 2).forEach(value -> {
            RecipeCommand recipe = RecipeCommand.builder().description(UUID.randomUUID().toString()).build();
            recipeCommandSet.add(recipe);
        });

        when(recipeService.findAll()).thenReturn(recipeCommandSet);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        String page = recipeController.getRecipes(model);

        assertThat(page).isEqualTo("recipe/recipe_list");
        verify(recipeService, times(1)).findAll();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> recipesInController = argumentCaptor.getValue();
        assertThat(recipesInController).isEqualTo(recipeCommandSet);

        //assertThat(model.getAttribute("recipes")).isEqualTo(recipes);
    }
}
