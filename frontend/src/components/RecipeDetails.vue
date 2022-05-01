<template>
  <div v-if="currentRecipe.id" class="edit-form">
    <h4>Recipe</h4>
    <form>
      <label for="name">Name</label>
      <input
        type="text"
        class="form-control"
        id="name"
        v-model="currentRecipe.name"
      />
      <label for="description">Description</label>
      <input
        type="text"
        class="form-control"
        id="description"
        v-model="currentRecipe.description"
      />
    </form>
    <button class="button-danger" @click="deleteRecipe">Delete</button>
    <button type="submit" class="button-primary" @click="updateRecipe">
      Update
    </button>
    <p>{{ message }}</p>
  </div>
  <div v-else>
    <br />
    <p>Please click on a Recipe...</p>
  </div>
</template>
<script lang="ts">
import { defineComponent } from "vue";
import RecipeDataService from "../services/RecipeDataService";
import type Recipe from "../types/Recipe";
import type ResponseData from "../types/ResponseData";
export default defineComponent({
  name: "recipe",
  data() {
    return {
      currentRecipe: {} as Recipe,
      message: "",
    };
  },
  methods: {
    getRecipe(id: any) {
      RecipeDataService.get(id)
        .then((response: ResponseData) => {
          this.currentRecipe = response.data;
          console.log(response.data);
        })
        .catch((e: Error) => {
          console.log(e);
        });
    },
    updatePublished(status: boolean) {
      let data = {
        id: this.currentRecipe.id,
        name: this.currentRecipe.name,
        description: this.currentRecipe.description,
      };
      RecipeDataService.update(this.currentRecipe.id, data)
        .then((response: ResponseData) => {
          console.log(response.data);
          this.message = "The status was updated successfully!";
        })
        .catch((e: Error) => {
          console.log(e);
        });
    },
    updateRecipe() {
      RecipeDataService.update(this.currentRecipe.id, this.currentRecipe)
        .then((response: ResponseData) => {
          console.log(response.data);
          this.message = "The recipe was updated successfully!";
        })
        .catch((e: Error) => {
          console.log(e);
        });
    },
    deleteRecipe() {
      RecipeDataService.delete(this.currentRecipe.id)
        .then((response: ResponseData) => {
          console.log(response.data);
          this.$router.push({ name: "recipes" });
        })
        .catch((e: Error) => {
          console.log(e);
        });
    },
  },
  mounted() {
    this.message = "";
    this.getRecipe(this.$route.params.id);
  },
});
</script>
<style>
</style>
