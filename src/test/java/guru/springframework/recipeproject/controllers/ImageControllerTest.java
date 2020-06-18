package guru.springframework.recipeproject.controllers;

import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.exceptions.NotFoundException;
import guru.springframework.recipeproject.model.Recipe;
import guru.springframework.recipeproject.service.ImageService;
import guru.springframework.recipeproject.service.RecipeService;
import guru.springframework.recipeproject.util.VariableUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    @InjectMocks
    ImageController imageController;

    MockMvc mockMvc;


    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    public void testGetRecipeImageNotFound() throws Exception {
        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipes/1/image"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error_page"))
                .andExpect(model().attribute("exception", Matchers.any(NotFoundException.class)))
                .andExpect(model().attribute("httpStatus", Matchers.equalTo(HttpStatus.NOT_FOUND)));

        verify(recipeService).findById(1L);
    }

    @Test
    public void testGetRecipeImageFormatError() throws Exception {
        mockMvc.perform(get("/recipes/asdas/image"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error_page"))
                .andExpect(model().attribute("exception", Matchers.any(NumberFormatException.class)))
                .andExpect(model().attribute("httpStatus", Matchers.equalTo(HttpStatus.BAD_REQUEST)));
    }

    @Test
    public void testGetImageForm() throws Exception {
        RecipeCommand recipeCommand = RecipeCommand.builder().id(1L).description("Some recipe").build();

        when(recipeService.findById(1L)).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipes/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipe_image_upload"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findById(1L);
    }

    @Test
    public void testSaveImage() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("imageFile", "image.txt"
                , "text/plain", "Spring Framework Guru".getBytes());

        mockMvc.perform(multipart("/recipes/1/image").file(mockMultipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes/1/update"))
                .andExpect(header().string("Location", "/recipes/1/update"));

        verify(imageService, times(1)).saveImageFile(eq(1L), any());
    }

    @Test
    public void testRenderImageFromDB() throws Exception {
        RecipeCommand recipeCommand = RecipeCommand.builder().id(1L).build();

        String s = "fake image text";
        Byte[] imageBytes = VariableUtil.boxByteArray(s.getBytes());

        recipeCommand.setImage(imageBytes);

        when(recipeService.findById(1L)).thenReturn(recipeCommand);

        MockHttpServletResponse response = mockMvc.perform(get("/recipes/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();

        assertThat(s.getBytes().length).isEqualTo(responseBytes.length);
    }


}