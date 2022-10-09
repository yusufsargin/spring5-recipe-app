package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class IngredientController {
    private final RecipeService recipeService;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{id}/ingredients")
    public String getIngredientList(@PathVariable String id, Model model) throws Exception {
        RecipeCommand command = recipeService.findCommandById(Long.valueOf(id));

        model.addAttribute("recipe", command);

        return "recipe/ingredient/list";
    }
}
