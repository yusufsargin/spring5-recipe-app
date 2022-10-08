package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by jt on 6/19/17.
 */
public class RecipeControllerTest {


    @Mock
    RecipeService recipeService;

    RecipeController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetRecipe() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testGetNewRecipeForm() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();

        command.setId(1L);

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("description", "some thing"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(String.format("redirect:/recipe/show/%s", command.getId())));
    }

    @Test
    public void testUpdateNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();

        command.setId(1L);
        command.setDescription("some thing");

        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testDeleteRecipeAction() throws Exception {
        mockMvc.perform(delete("/recipe/1/delete")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(view().name("redirect:/"))
                .andExpect(status().is3xxRedirection());

        verify(recipeService, times(1)).deleteById(anyLong());
    }
}
