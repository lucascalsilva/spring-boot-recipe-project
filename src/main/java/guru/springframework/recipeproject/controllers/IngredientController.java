package guru.springframework.recipeproject.controllers;

import guru.springframework.recipeproject.commands.IngredientCommand;
import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.commands.UnitOfMeasureCommand;
import guru.springframework.recipeproject.service.IngredientService;
import guru.springframework.recipeproject.service.UnitOfMeasureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recipes/{recipeId}/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    @GetMapping
    public String getIngredientsByRecipeId(@PathVariable Long recipeId, Model model){
        Set<IngredientCommand> ingredients = ingredientService.findByRecipeId(recipeId);
        model.addAttribute("ingredients", ingredients);
        return "recipe/ingredient/ingredient_list";
    }

    @GetMapping("/{ingredientId}/show")
    public String getIngredientById(@PathVariable Long ingredientId, Model model){
        IngredientCommand ingredientCommand = ingredientService.findById(ingredientId);

        model.addAttribute("ingredient", ingredientCommand);
        return "recipe/ingredient/ingredient_detail";
    }

    @GetMapping("/{ingredientId}/update")
    public String updateIngredientById(@PathVariable Long ingredientId, Model model){
        IngredientCommand ingredientCommand = ingredientService.findById(ingredientId);
        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("unitOfMeasures", unitOfMeasureService.findAll());
        return "recipe/ingredient/ingredient_merge";
    }

    @GetMapping("/new")
    public String newIngredient(@PathVariable Long recipeId, Model model){
        //TODO CHECK IF RECIPE EXISTS
        Set<UnitOfMeasureCommand> unitOfMeasures = unitOfMeasureService.findAll();
        model.addAttribute("unitOfMeasures", unitOfMeasures);
        model.addAttribute("ingredient", IngredientCommand.builder().recipe(RecipeCommand.builder().id(recipeId).build())
                .unitOfMeasure(unitOfMeasures.iterator().next()).build());

        return "recipe/ingredient/ingredient_merge";
    }

    //Save or updates
    @PostMapping
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        IngredientCommand savedIngredient = ingredientService.save(command);
        return "redirect:/recipes/" + savedIngredient.getRecipe().getId() +  "/ingredients";
    }

    @GetMapping("/{ingredientId}/delete")
    public String delete(@PathVariable Long recipeId, @PathVariable Long ingredientId){
        ingredientService.deleteById(ingredientId);
        return "redirect:/recipes/" + recipeId + "/ingredients";
    }
}
