import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue')
    },
    {
      path: "/",
      alias: "/recipes",
      name: "recipes",
      component: () => import("../views/RecipesSearchView.vue")
    },
    {
      path: "/recipes/:id",
      name: "recipe-details",
      component: () => import("../views/RecipeView.vue")
    },
    {
      path: "/add",
      name: "add",
      component: () => import("../views/AddRecipeView.vue")
    }
  ]
})

export default router
