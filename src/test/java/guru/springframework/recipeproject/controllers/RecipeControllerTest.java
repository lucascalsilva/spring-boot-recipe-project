package guru.springframework.recipeproject.controllers;

import guru.springframework.recipeproject.model.Recipe;
import guru.springframework.recipeproject.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    RecipeController recipeController;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);
    }

    @Test
    public void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe_list"));
    }

    @Test
    public void givenThreeRecipesNeeded_whenGettingAllRecipes_thenReturnThreeRecipes(){
        Set<Recipe> recipes = new HashSet<Recipe>();

        IntStream.range(0, 2).forEach(value -> {
            Recipe recipe = Recipe.builder().description(UUID.randomUUID().toString()).build();
            recipes.add(recipe);
        });

        when(recipeService.findAll()).thenReturn(recipes);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        String page = recipeController.getRecipes(model);

        assertThat(page).isEqualTo("recipe_list");
        verify(recipeService, times(1)).findAll();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> recipesInController = argumentCaptor.getValue();
        assertThat(recipesInController).isEqualTo(recipes);

        //assertThat(model.getAttribute("recipes")).isEqualTo(recipes);

    }
}
