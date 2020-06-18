package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.commands.CategoryCommand;
import guru.springframework.recipeproject.converters.CategoryCommandToCategory;
import guru.springframework.recipeproject.converters.CategoryToCategoryCommand;
import guru.springframework.recipeproject.model.Category;
import guru.springframework.recipeproject.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private final CategoryCommandToCategory categoryCommandToCategory;

    @Override
    public Set<CategoryCommand> findAll() {
        Set<Category> categories = new HashSet<>();
        categoryRepository.findAll().iterator().forEachRemaining(categoryToCategoryCommand::convert);
        return categories.stream().map(categoryToCategoryCommand::convert).collect(Collectors.toSet());
    }
}
