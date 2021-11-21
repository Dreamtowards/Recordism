<template>
<div>

  <div class="thumbnail p-3">
    <div>
      <h4>Account Information</h4>
    </div>
    <hr>
    <div>
      <fieldset class="form-horizontal">
        <div class="control-group">
          <label>Username:</label>
          <div class="controls">
            <input type="text" :value="user.username" disabled>
          </div>
        </div>

      </fieldset>

      <button @click="userLogout">Log out</button>
    </div>
  </div>


  <div class="thumbnail p-3 mt-3">
    <div>
      <h4>My Sites</h4>
    </div>
    <hr>
    <div v-for="site in user.sites">
      <router-link :to="'/site/'+site.id">{{site.address}}</router-link>
    </div>
    <hr>
    <button @click="requestSiteNew">Register Site</button>
  </div>
</div>
</template>

<script>

import {request, user, userLogout, userUpdate, showToast} from "../main";

export default {
  name: "UserProfile",
  data() {return {
    user: user
  }},
  methods: {

    userLogout() {

      userLogout();
    },

    requestSiteNew() {
      let siteAddr = prompt("Site Address");

      request("/site_new", {
        site_address: siteAddr
      }, resp => {

        userUpdate();
        showToast("Success create site.");
      });
    }
  }
}
</script>

<style scoped>

</style>