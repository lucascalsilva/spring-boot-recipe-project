package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.model.Recipe;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    ImageServiceImpl imageService;

    @Test
    void saveImageFile() throws Exception {
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Spring Framework Guru".getBytes());

        Long recipeId = 1L;

        Recipe recipe = Recipe.builder().id(recipeId).build();

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        imageService.saveImageFile(recipeId, multipartFile);

        verify(recipeRepository, times(1)).findById(1L);
        verify(recipeRepository, times(1)).save(argumentCaptor.capture());

        Recipe savedRecipe = argumentCaptor.getValue();

        assertThat(multipartFile.getBytes().length).isEqualTo(savedRecipe.getImage().length);

    }
}