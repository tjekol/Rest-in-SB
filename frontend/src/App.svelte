<script>
  import user from "./store/user.js";
  import Login from "./lib/Login.svelte";

  $: isLoggedIn = $user === null ? false : true;

  const logout = ()  => {
    user.update(val => val = null);
  }
</script>

<div class="content">
  <h1>
    Welcome to Smart Voting App!👋🏽
  </h1>
  <div>
  {#if isLoggedIn}
    <h2>Hello, {$user.username}</h2>
    <input type="button" value="Logout" on:click={logout}>
  {:else}
    <h2>Please log in to create a Poll!</h2>
    <Login/>
  {/if}
  </div>
</div>