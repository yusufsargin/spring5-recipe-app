package guru.springframework.controllers;

import guru.springframework.services.RecipeService;
import junit.framework.TestCase;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class IngredientControllerTest extends TestCase {
    IngredientController controller;

    @Mock
    RecipeService recipeService;

    MockMvc mockMvc;

    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);

        controller = new IngredientController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    public void testGetIngredientList() throws Exception {
        mockMvc.perform(get(String.format("/recipe/%s/ingredients",1)))
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(status().isOk());

        verify(recipeService,times(1)).findCommandById(anyLong());
    }
}
