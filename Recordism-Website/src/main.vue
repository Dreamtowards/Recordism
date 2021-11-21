<template>
<div>
  <div class="w-100 navbar shadow">
    <div class="container">
      <router-link to="/" style="color: #fff;font-weight: 100; font-size: 16px; padding-right: 20px;">Recordism</router-link>

      <div class="float-end">
        <div v-if="user.token">
          <router-link to="/profile">{{ user.username }}</router-link>
        </div>
        <div v-else>
          <router-link to="/login">Login</router-link>
          <router-link to="/register">Register</router-link>
        </div>
      </div>
    </div>
  </div>

  <div class="container" style="margin-top: 60px;">

    <router-view></router-view>

    <br><br><br><br>
    <p style="font-size: 12px;color: #888;">© Copyright 2021 <a href="#">Recordism</a> | <a href="#">Privacy Policy</a></p>
    <br><br>
  </div>
</div>
</template>

<script>
import {user, userUpdate} from "./main.js";

export default {
  components: { },
  data() { return {
      user: user
  }},
  methods: {
    pollToast(msg) {
      alert(msg);
    }
  },
  mounted() {

    userUpdate();
  }
};
</script>




<style scoped>

.card-dash-info {
  border-radius: 4px;
  padding: 18px;
  text-align: center;
  width: 100%;
}

.navbar {
  color: #fff;
  height: 40px;
  background-image: linear-gradient(#333333, #222222);
  box-shadow: 0 1px 3px rgba(0 0 0, .25), inset 0 -1px 0 rgba(0 0 0, .10);
}
.navbar a {
  color: #bfbfbf;
  text-shadow: 0 -1px 0 rgba(0 0 0, .25);
  line-height: 19px;
  font-size: 13px;
  padding: 9px;
  display: inline-block;
}

</style>