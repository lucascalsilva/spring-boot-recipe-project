package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.exceptions.NotFoundException;
import guru.springframework.recipeproject.model.Recipe;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import guru.springframework.recipeproject.util.VariableUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        try {
            log.info("Saving image {} for recipe id {}...", file.getName(), recipeId);
            Recipe recipeById = recipeRepository.findById(recipeId).orElseThrow(() ->
                    new NotFoundException(Recipe.class, "id", recipeId.toString()));

            recipeById.setImage(VariableUtil.boxByteArray(file.getBytes()));

            recipeRepository.save(recipeById);
        } catch (IOException e) {
            log.error("Error when saving image for recipe with id {}", recipeId, e);
        }
    }
}
