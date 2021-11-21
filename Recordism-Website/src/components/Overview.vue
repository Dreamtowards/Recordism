<template>
<div>

<!--    <div class="hstack mb-4" >-->
<!--      <div class="card-dash-info">-->
<!--        <h6 class="color-text-secondary">Online Users</h6>-->
<!--        <h2>{{ site_metrics.online_users }}</h2>-->
<!--      </div>-->
<!--      <div class="card-dash-info">-->
<!--        <h6 class="color-text-secondary">Online Pages</h6>-->
<!--        <h2>{{ site_metrics.online_pages }}</h2>-->
<!--      </div>-->
<!--      <div class="card-dash-info">-->
<!--        <h6  class="color-text-secondary">Today Pageviews</h6>-->
<!--        <h2 style="">-1</h2>-->
<!--      </div>-->
<!--      <div class="card-dash-info">-->
<!--        <h6  class="color-text-secondary">Today Viewers</h6>-->
<!--        <h2 style="">-1</h2>-->
<!--      </div>-->
<!--    </div>-->

    <Dashboard></Dashboard>

    <AccessTable :site_id="site_id" ></AccessTable>

    <br><br>

<!--    <StatisticsPanel></StatisticsPanel>-->

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
  props: {"site_id": Number},
  components: {StatisticsPanel, Dashboard, AccessTable},
  data() {return{
    user: user,

    site_metrics: {
      online_users: -1,
      online_pages: -1,
    }
  }},
  methods: {

  },
  mounted() {




    setInterval(() => {

      // request("/site_overview", {site_id: this.site_id}, resp => {
      //   this.site_metrics.online_users = resp.online_users;
      //   this.site_metrics.online_pages = resp.online_pages;
      // });
    }, 2000)
  }
}
</script>





