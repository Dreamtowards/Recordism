<template>
<div>

  <div>
    <h3>Register</h3>
    <hr>
    <input type="text" placeholder="Username" v-model="username"><br>
    <input type="password" placeholder="Password" v-model="password"><br>
    <button :disabled="!username || !password" @click="requestUserRegister">Register</button>
  </div>

</div>
</template>

<script>

import {request} from "../main";
import sha256 from "sha256";


export default {
  name: "UserRegister",
  data() {return {
    username: '',
    password: ''
  }},
  methods: {

    requestUserRegister() {

      request("/user_register", {
        username: this.username,
        password_digest: sha256(this.password)
      }, resp => {

        alert("Success "+resp.user_id);
      });
    }
  }
}
</script>

<style scoped>

</style>