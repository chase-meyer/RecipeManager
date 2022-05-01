<template>
  <div v-if="!submitted">
    <form>
      <label for="name">Name</label>
      <input
        type="text"
        class="u-full-width"
        id="name"
        required
        v-model="recipe.name"
        name="name"
      />
      <label for="description">Description</label>
      <input
        type="text"
        class="u-full-width"
        id="description"
        required
        v-model="recipe.description"
        name="description"
      />
    </form>
    <button
      @click="saveRecipe"
      class="button-primary"
      type="submit"
      value="Submit"
    >
      Submit
    </button>
  </div>
  <div v-else>
    <h4>You submitted successfully!</h4>
    <button class="button button-primary" @click="newRecipe">Add</button>
  </div>
</template>
<script lang="ts">
import { defineComponent } from "vue";
import RecipeDataService from "../services/RecipeDataService";
import type Recipe from "../types/Recipe";
import type ResponseData from "../types/ResponseData";
export default defineComponent({
  name: "add-recipe",
  data() {
    return {
      recipe: {
        id: null,
        name: "",
        description: "",
      } as Recipe,
      submitted: false,
    };
  },
  methods: {
    saveRecipe() {
      let data = {
        name: this.recipe.name,
        description: this.recipe.description,
      };
      console.log(data);
      RecipeDataService.create(data)
        .then((response: ResponseData) => {
          this.recipe.id = response.data.id;
          console.log(response.data);
          this.submitted = true;
        })
        .catch((e: Error) => {
          console.log(e);
        });
    },
    newRecipe() {
      this.submitted = false;
      this.recipe = {} as Recipe;
    },
  },
});
</script>
<style>
</style>
