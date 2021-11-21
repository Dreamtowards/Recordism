<template>
<div>
  <div class="w-100 navbar shadow">
    <div class="container">
      <a style="color: #fff;font-weight: 100; font-size: 16px; padding-right: 20px;">Recordism</a>
      <a>Profile</a>
      <a>MySites</a>

      <div class="float-end">
        <div v-if="user.uid">
          <a>Username</a>
        </div>
        <div v-else>
          <a>Login</a>
        </div>
      </div>
    </div>
  </div>

  <div class="container" style="margin-top: 60px;">
    <!-- HEADER -->

    <div class="hstack mb-4" >
      <div class="card-dash-info">
        <h6 class="color-text-secondary">Online Users</h6>
        <h2>{{ site_metrics.online_users }}</h2>
      </div>
      <div class="card-dash-info">
        <h6 class="color-text-secondary">Online Pages</h6>
        <h2>{{ site_metrics.online_pages }}</h2>
      </div>
      <div class="card-dash-info">
        <h6  class="color-text-secondary">Today Pageviews</h6>
        <h2 style="">-1</h2>
      </div>
      <div class="card-dash-info">
        <h6  class="color-text-secondary">Today Viewers</h6>
        <h2 style="">-1</h2>
      </div>
    </div>


    <Dashboard></Dashboard>

    <AccessTable :site_id="1" ></AccessTable>

    <br><br>

<!--    <StatisticsPanel></StatisticsPanel>-->





    <br><br><br><br>
    <p style="font-size: 12px;color: #888;">© Copyright 2021 <a href="#">Recordism</a> | <a href="#">Privacy Policy</a></p>
    <br><br>
  </div>

</div>
</template>

<script>

import {user, request} from "../main.js";

import AccessTable from "./widgets/AccessTable.vue";
import Dashboard from "./widgets/Dashboard.vue";
import StatisticsPanel from "./widgets/StatisticsPanel.vue";


import "../assets/css/morris.css";
import "../assets/js/morris.js";

export default {
  name: "Overview",
  components: {StatisticsPanel, Dashboard, AccessTable},
  data() {return{
    user: user,

    site_metrics: {
      online_users: -1,
      online_pages: -1,
    }
  }},
  methods: {
    request_site_metrics() {
      request("/api/site_overview", {site_id: 1}, resp => {
        this.site_metrics.online_users = resp.online_users;
        this.site_metrics.online_pages = resp.online_pages;
      });
    },
  },
  mounted() {




    setInterval(() => {

      this.request_site_metrics();
    }, 2000)
  }
}
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