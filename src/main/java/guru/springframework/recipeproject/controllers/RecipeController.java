package guru.springframework.recipeproject.controllers;

import guru.springframework.recipeproject.model.Recipe;
import guru.springframework.recipeproject.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping
    public String getRecipes(Model model){
        model.addAttribute("recipes", recipeService.findAll());
        return "recipes";
    }

    @GetMapping("/{id}")
    public String getRecipes(Model model, @PathVariable Long id){
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);
        return "recipe_detail";
    }
}
