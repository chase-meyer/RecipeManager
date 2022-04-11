package com.chase.backend.repository;

import java.util.List;
import com.chase.backend.model.Recipe;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RecipeRepository extends MongoRepository<Recipe, String> {

    @Query("{name:'?0'}")
    Recipe findRecipeByName(String name);

    @Query(value = "{category:'?0'}", fields = "{'name' : 1, 'quantity' : 1}")
    List<Recipe> findAll(String category);

    public long count();

}