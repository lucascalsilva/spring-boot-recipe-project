package guru.springframework.recipeproject.controllers;

import guru.springframework.recipeproject.model.Recipe;
import guru.springframework.recipeproject.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipes")
@RequiredArgsConstructor
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public String getRecipes(Model model){
        log.debug("Getting all recipes in {}", this.getClass().getSimpleName());
        model.addAttribute("recipes", recipeService.findAll());
        return "recipe_list";
    }

    @GetMapping("/{id}")
    public String getRecipeByID(Model model, @PathVariable Long id){
        log.debug("Getting recipe in {} by id {}", this.getClass().getSimpleName(), id);
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);
        return "recipe_detail";
    }
}
