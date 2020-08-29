package guru.springframework.recipeproject.bootstrap;

import guru.springframework.recipeproject.model.*;
import guru.springframework.recipeproject.repositories.CategoryRepository;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import guru.springframework.recipeproject.repositories.UnitOfMeasureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoaderMySQL implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(categoryRepository.count() == 0L){
            loadCategories();
        }

        if(unitOfMeasureRepository.count() == 0L){
            loadUnitOfMeasures();
        }
    }

    private void loadCategories() {
        categoryRepository.save(Category.builder().name("American").build());
        categoryRepository.save(Category.builder().name("Italian").build());
        categoryRepository.save(Category.builder().name("Mexican").build());
        categoryRepository.save(Category.builder().name("Fast Food").build());
    }

    private void loadUnitOfMeasures() {
        unitOfMeasureRepository.save(UnitOfMeasure.builder().description("Teaspoon").build());
        unitOfMeasureRepository.save(UnitOfMeasure.builder().description("Tablespoon").build());
        unitOfMeasureRepository.save(UnitOfMeasure.builder().description("Cup").build());
        unitOfMeasureRepository.save(UnitOfMeasure.builder().description("Pinch").build());
        unitOfMeasureRepository.save(UnitOfMeasure.builder().description("Ounce").build());
        unitOfMeasureRepository.save(UnitOfMeasure.builder().description("Each").build());
        unitOfMeasureRepository.save(UnitOfMeasure.builder().description("Pint").build());
        unitOfMeasureRepository.save(UnitOfMeasure.builder().description("Dash").build());
    }
}
