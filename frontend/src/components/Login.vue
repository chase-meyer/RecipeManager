<template>
  <div class="container">
    <div class="flex-centered">
      <h1>Login</h1>
      <form>
        <div class="row">
          <label for="userEmail">Email</label>
          <input
            class="u-full-width"
            type="email"
            placeholder="example@example.com"
            id="userEmail"
          />
        </div>

        <div class="row">
          <label for="password">Password</label>
          <input
            class="u-full-width"
            type="password"
            placeholder="password"
            id="password"
          />
        </div>

        <input class="button-primary" type="submit" value="Submit" />
      </form>
    </div>
  </div>
</template>

