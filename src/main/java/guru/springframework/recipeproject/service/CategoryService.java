package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.commands.CategoryCommand;

import java.util.Set;

public interface CategoryService {

    Set<CategoryCommand> findAll();
}
