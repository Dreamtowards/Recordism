<template>
<div>
  <div class="width-full" style="height: 160px;background: linear-gradient(#6a707a, #0d1117);border-bottom: 2px solid #888;">

  </div>
  <div class="col-7" style="margin: 0 auto;margin-top: -94px;">
    <!-- HEADER -->
    <div>
      <a class="h1 color-text-white no-underline page-title" href="#">Recordism</a>
      <div class="d-inline-block mx-2" style="height: 24px;width: 2px;background: #666;"/>
      <span class="color-text-secondary" style="vertical-align: super">Everything your tech support team need to know!</span>
    </div>

    <div style="height: 300px;border-radius: 10px;">

      <div class="page-navbar">
        <a href="#">Console</a> / <a>Overview</a>
        <input type="text" placeholder="Search" class="float-right search" style="margin-top: -3px;">
      </div>

      <div style="margin: 0 auto;margin-top: 24px;text-shadow: #fff 0 1px 0;">
        <div class="card-dash-info col-3">
          <h6  class="color-text-secondary">Online Users</h6>
          <h2>31</h2>
        </div>
        <div class="card-dash-info col-4">
          <h6  class="color-text-secondary">Today Pageviews</h6>
          <h2 style="">52</h2>
        </div>
        <div class="card-dash-info col-4">
          <h6  class="color-text-secondary">Today Viewers</h6>
          <h2 style="">31</h2>
        </div>
      </div>

<!--      <div>-->
<!--        come Sources Chart-->
<!--      </div>-->

      <div style="font-size: 90%">
        <h4>Recent Access</h4>
        <a class="ui button">Button</a>
        <table class="ui  table">
          <tr>
            <th>Time</th>
            <th>Location</th>
            <th>Duration</th>
            <th title="User Browser Fingerprint">FP</th>
            <th>CUID</th>
            <th>BTZ</th>
            <th>BLG</th>
            <th>DVC</th>
            <th>Browser</th>
            <th>OS</th>
            <th>Screen BH</th>
            <th>IP</th>
          </tr>
          <tr>
            <td>2021-07-21</td>
            <td>🇹🇴&nbsp; Taipei, Taiwan</td>
            <td>12min *alive</td>
            <td>b59581...</td>
            <td>345eb4...</td>
            <td>Asia/Shanghai</td>
            <td>en(en,zh-CN,zh)</td>
            <td>MacIntel</td>
            <td>Chrome 89.2</td>
            <td>Win10</td>
            <td>2K<sup>2560x1440</sup> B/80%</td>
            <td>1.42.51.148</td>
          </tr>
        </table>
      </div>

    </div>

    <br><br><br>
    <p style="font-size: 12px;color: #888;">© Copyright 2021 <a href="#">Recordism</a> | <a href="#">Privacy Policy</a></p>
  </div>

</div>
</template>

<script>
export default {
  name: "Overview"
}
</script>

<style scoped>

.page-title {
  text-shadow: #555 0 2px 1px;
  font-size: 38px!important;
}

.page-navbar a {
  color: #6b6b6b;
  font-family: "PT Sans", "Helvetica Neue", Arial, sans-serif;
  text-shadow: #ebebeb 0 1px;
  text-decoration: none;
  font-size: 1.1em;
  padding: .1em 0;
  line-height: 1.5em;
}

.page-navbar {
  background: url("../assets/images/noise.png"), linear-gradient(#f0f0f0, #d8d8d8, #c0c0c0);
  margin-top: 20px;
  padding: 10px 16px;
  border-radius: 10px;
  border-top: 1px solid #f2f2f2;
  border-bottom: 1px solid #8c8c8c;
}

.page-navbar .search {
  padding: .15em .5em .15em;
  font-family: "PT Sans", "Helvetica Neue", Arial, sans-serif;
  border-radius: 0.5em;
  box-shadow: #d1d1d1 0 1px;
  background-color: #f2f2f2;
  border: 1px solid #b3b3b3;
  color: #888;
}

.page-navbar .search:focus {
  color: #444;
  border-color: #80b1df;
  box-shadow: #80b1df 0 0 4px, #80b1df 0 0 3px inset;
  background-color: #fff;
  outline: none;
}

.card-dash-info {
  display: inline-block;
  /*background:linear-gradient(#d5d5e5, #d5d5d5);*/
  border-radius: 4px;
  padding: 18px;
  /*width: 24.6%;*/
  /*border-top: 1px solid #f2f2f2;*/
  /*border-bottom: 1px solid #8c8c8c;*/

}

</style>