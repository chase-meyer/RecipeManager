<script setup lang="ts">
import { RouterLink, RouterView } from "vue-router";
import HelloWorld from "@/components/HelloWorld.vue";
</script>

<template>
  <header>
    <nav class="navbar">
      <div class="container">
        <div class="row">
          <div class="nine columns">
            <ul class="navbar-list">
              <li class="navbar-item">
                <RouterLink class="navbar-link" to="/">Home</RouterLink>
              </li>
              <li class="navbar-item">
                <RouterLink class="navbar-link" to="/about">About</RouterLink>
              </li>
            </ul>
          </div>
          <div class="three columns">
            <RouterLink class="button navbar-button" to="/login"
              >Login</RouterLink
            >
          </div>
        </div>
      </div>
    </nav>
  </header>

  <RouterView />
</template>


<style>
header {
  margin-bottom: 6.5rem;
}

.navbar {
  position: fixed;
  top: 0;
  left: 0;
  display: block;
  width: 100%;
  height: 6.5rem;
  background: #fff;
  z-index: 99;
  border-top: 1px solid #eee;
  border-bottom: 1px solid #eee;
}

.navbar-list {
  list-style: none;
  margin-bottom: 0;
}

.navbar-item {
  position: relative;
  float: left;
  margin-bottom: 0;
}

.navbar-link {
  text-transform: uppercase;
  font-size: 11px;
  font-weight: 600;
  letter-spacing: 0.2rem;
  margin-right: 35px;
  text-decoration: none;
  line-height: 6.5rem;
  color: #222;
}

.navbar-button {
  margin-top: 1.3rem;
}
</style>

