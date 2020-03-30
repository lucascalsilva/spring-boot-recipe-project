package guru.springframework.recipeproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"", "/", "/index", "/index.html", "/index.htm"})
    public String getIndexPage(){
        System.out.println("Testing... 12345");
        return "index";
    }
}
