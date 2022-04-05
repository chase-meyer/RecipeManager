<script setup lang="ts">
defineProps<{
  msg: string;
}>();
</script>

<template>
  <div class="container">
    <h1>{{ msg }}</h1>
    <h3>Search Recipes</h3>
    <form>
      <input
        class="u-full-width"
        type="search"
        placeholder="find a recipe"
        id="recipeSearch"
      />
      <input class="button-primary" type="submit" value="Submit" />
    </form>
  </div>
</template>