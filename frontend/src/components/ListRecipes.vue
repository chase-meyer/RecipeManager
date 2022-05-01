<template>
  <div class="row">
    <div class="eight columns">
      <form>
        <input
          type="text"
          class="u-full-width"
          placeholder="Search by name"
          v-model="name"
        />
        <button
          class="button-primary"
          @click="searchName"
          type="submit"
          value="Submit"
        >
          Submit
        </button>
      </form>
    </div>
    <div class="six columns">
      <h4>Recipe List</h4>
      <ul>
        <li
          :class="{ active: index == currentIndex }"
          v-for="(recipe, index) in recipes"
          :key="index"
          @click="setActiveRecipe(recipe, index)"
        >
          {{ recipe.name }}
        </li>
      </ul>
      <button class="button-danger" @click="removeAllRecipes">
        Remove All
      </button>
    </div>
    <div class="six columns">
      <div v-if="currentRecipe.id">
        <h4>Recipe</h4>
        <div>
          <label><strong>Name:</strong></label> {{ currentRecipe.name }}
        </div>
        <div>
          <label><strong>Description:</strong></label>
          {{ currentRecipe.description }}
        </div>
        <router-link :to="'/recipes/' + currentRecipe.id">Edit</router-link>
      </div>
      <div v-else>
        <br />
        <p>Please click on a Recipe...</p>
      </div>
    </div>
  </div>
</template>
<script lang="ts">
import { defineComponent } from "vue";
import RecipeDataService from "../services/RecipeDataService";
import type Recipe from "../types/Recipe";
import type ResponseData from "../types/ResponseData";
export default defineComponent({
  name: "recipes-list",
  data() {
    return {
      recipes: [] as Recipe[],
      currentRecipe: {} as Recipe,
      currentIndex: -1,
      name: "",
    };
  },
  methods: {
    retrieveRecipes() {
      RecipeDataService.getAll()
        .then((response: ResponseData) => {
          this.recipes = response.data;
          console.log(response.data);
        })
        .catch((e: Error) => {
          console.log(e);
        });
    },
    refreshList() {
      this.retrieveRecipes();
      this.currentRecipe = {} as Recipe;
      this.currentIndex = -1;
    },
    setActiveRecipe(recipe: Recipe, index = -1) {
      this.currentRecipe = recipe;
      this.currentIndex = index;
    },
    removeAllRecipes() {
      RecipeDataService.deleteAll()
        .then((response: ResponseData) => {
          console.log(response.data);
          this.refreshList();
        })
        .catch((e: Error) => {
          console.log(e);
        });
    },
    searchName() {
      RecipeDataService.findByName(this.name)
        .then((response: ResponseData) => {
          this.recipes = response.data;
          this.setActiveRecipe({} as Recipe);
          console.log(response.data);
        })
        .catch((e: Error) => {
          console.log(e);
        });
    },
  },
  mounted() {
    this.retrieveRecipes();
  },
});
</script>
<style>
.list {
  text-align: left;
  max-width: 750px;
  margin: auto;
}
</style>
