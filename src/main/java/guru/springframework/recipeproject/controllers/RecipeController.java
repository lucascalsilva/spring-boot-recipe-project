package guru.springframework.recipeproject.controllers;

import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.exceptions.NotFoundException;
import guru.springframework.recipeproject.service.CategoryService;
import guru.springframework.recipeproject.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/recipes")
@RequiredArgsConstructor
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;
    private final CategoryService categoryService;

    //Shows the list of recipes
    @GetMapping
    public String getRecipes(Model model){
        log.debug("Getting all recipes in {}", this.getClass().getSimpleName());
        model.addAttribute("recipes", recipeService.findAll());
        return "recipe/recipe_list";
    }

    //Shows the view form
    @GetMapping("/{id}/show")
    public String getRecipeById(Model model, @PathVariable String id){
        log.debug("Getting recipe in {} by id {}", this.getClass().getSimpleName(), id);
        RecipeCommand recipeCommand = recipeService.findById(Long.valueOf(id));
        model.addAttribute("recipe", recipeCommand);
        return "recipe/recipe_detail";
    }

    //Shows the update form
    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/recipe_merge";
    }

    //Shows the new form
    @GetMapping("/new")
    public String newRecipe(Model model){
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipe_merge";
    }

    //Save or updates
    @PostMapping
    public String saveOrUpdate(@ModelAttribute("recipe") @Valid RecipeCommand command, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError ->
                    log.error(objectError.toString()));

            return "recipe/recipe_merge";
        }

        RecipeCommand savedRecipeCommand = recipeService.save(command);
        return "redirect:/recipes/" + savedRecipeCommand.getId() + "/update";
    }

    @GetMapping("/{id}/delete")
    public String deleteById(@PathVariable Long id, @ModelAttribute RecipeCommand command){
        recipeService.deleteById(id);
        return "redirect:/recipes";
    }


}
