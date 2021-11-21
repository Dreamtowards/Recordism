<template>
<div>
  <div>
    <h3>Login</h3>
    <hr>
    <input type="text" placeholder="Username" v-model="username"><br>
    <input type="password" placeholder="Password" v-model="password"><br>
    <button :disabled="!username || !password" @click="requestUserLogin">Login</button>
  </div>
</div>
</template>

<script>

import {request, userLogin, userUpdate} from "../main";
import sha256 from "sha256";


export default {
  name: "UserLogin",
  data() {return {
    username: '',
    password: ''
  }},
  methods: {

    requestUserLogin() {

      request("/user_login", {
        account: this.username,
        password_digest: sha256(this.password)
      }, resp => {

        userLogin(resp.token);
        userUpdate();
      });
    }
  }
}
</script>

<style scoped>

</style>