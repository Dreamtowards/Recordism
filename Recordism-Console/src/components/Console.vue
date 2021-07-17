<template>
<div>
  <div class="console-nav color-shadow-medium d-inline-block p-4">
    <router-link class="h3 color-text-secondary" to="/console" style="font-weight: 700;">Recordism™</router-link>

    <br><br>
    <div class="input-group">
      <input class="form-control" type="text" placeholder="Search...">
      <span class="input-group-button">
      <button class="btn" type="button" aria-label="Copy to clipboard">
        <svg class="octicon octicon-clippy" viewBox="0 0 14 16" version="1.1" width="14" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M2 13h4v1H2v-1zm5-6H2v1h5V7zm2 3V8l-3 3 3 3v-2h5v-2H9zM4.5 9H2v1h2.5V9zM2 12h2.5v-1H2v1zm9 1h1v2c-.02.28-.11.52-.3.7-.19.18-.42.28-.7.3H1c-.55 0-1-.45-1-1V4c0-.55.45-1 1-1h3c0-1.11.89-2 2-2 1.11 0 2 .89 2 2h3c.55 0 1 .45 1 1v5h-1V6H1v9h10v-2zM2 5h8c0-.55-.45-1-1-1H8c-.55 0-1-.45-1-1s-.45-1-1-1-1 .45-1 1-.45 1-1 1H3c-.55 0-1 .45-1 1z"></path></svg>
      </button>
    </span>
    </div>

    <br><br>

    <a class="Box anim-hover-grow btn button width-full text-center">SomeBtn</a>

  </div>
  <div class="d-inline-block v-align-top p-5">
    <a class="h0-mktg color-text-primary" style="font-weight: 700;">Dashboard</a>
    <nav>
      <ol>
        <li class="breadcrumb-item"><a href="https://github.com/business">Business</a></li>
        <li class="breadcrumb-item"><a href="https://github.com/business/customers">Customers</a></li>
        <li class="breadcrumb-item" aria-current="page">MailChimp</li>
      </ol>
    </nav>




  </div>
</div>
</template>

<script>
export default {
  name: "Console"
}
</script>

<style scoped>

.console-nav {
  width: 270px;
  background: #fff;
  height: 100vh;

}

</style>