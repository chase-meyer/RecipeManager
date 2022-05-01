package com.chase.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.chase.backend.model.Recipe;
import com.chase.backend.repository.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class RecipeController {
    @Autowired
    RecipeRepository recipeRepository;

    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> getAllRecipes(@RequestParam(required = false) String name) {
        try {
            List<Recipe> recipes = new ArrayList<Recipe>();
            if (name == null)
                recipeRepository.findAll().forEach(recipes::add);
            else
                recipeRepository.findByNameContaining(name).forEach(recipes::add);
            if (recipes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") String id) {
        Optional<Recipe> recipeData = recipeRepository.findById(id);
        if (recipeData.isPresent()) {
            return new ResponseEntity<>(recipeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        try {
            Recipe _recipe = recipeRepository
                    .save(new Recipe(recipe.getName(), recipe.getDescription()));
            return new ResponseEntity<>(_recipe, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/recipes/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable("id") String id, @RequestBody Recipe recipe) {
        Optional<Recipe> recipeData = recipeRepository.findById(id);
        if (recipeData.isPresent()) {
            Recipe _recipe = recipeData.get();
            if (recipe.getName() != null)
                _recipe.setName(recipe.getName());
            if (recipe.getDescription() != null)
                _recipe.setDescription(recipe.getDescription());
            if (recipe.getCategory() != null)
                _recipe.setCategory(recipe.getCategory());
            if (recipe.getIngredients() != null)
                _recipe.setIngredients(recipe.getIngredients());
            if (recipe.getInstructions() != null)
                _recipe.setInstructions(recipe.getInstructions());
            return new ResponseEntity<>(recipeRepository.save(_recipe), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable("id") String id) {
        try {
            recipeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/recipes")
    public ResponseEntity<HttpStatus> deleteAllRecipes() {
        try {
            recipeRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}