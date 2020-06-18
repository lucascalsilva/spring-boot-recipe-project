package guru.springframework.recipeproject.controllers;

import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.service.ImageService;
import guru.springframework.recipeproject.service.RecipeService;
import guru.springframework.recipeproject.util.VariableUtil;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recipes/{recipeId}")
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    @GetMapping("/image")
    public String getImageForm(@PathVariable String recipeId, Model model){
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(recipeId)));
        return "recipe/recipe_image_upload";
    }

    @PostMapping("/image")
    public String saveImage(@PathVariable String recipeId, @RequestParam("imageFile") MultipartFile multipartFile){
        imageService.saveImageFile(Long.valueOf(recipeId), multipartFile);
        return "redirect:/recipes/" + recipeId + "/update";
    }

    @GetMapping("/recipeimage")
    public void renderImageFromDB(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
        RecipeCommand recipeById = recipeService.findById(Long.valueOf(recipeId));

        if(recipeById.getImage() != null) {

            byte[] imageBytes = VariableUtil.unboxByteArray(recipeById.getImage());

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(imageBytes);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
