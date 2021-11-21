<template>

  <div class="thumbnail mb-4 p-0">
    <div class="p-3 border-bottom mb-3">
      <div class="btn-group d-inline-block float-end" data-toggle="buttons-radio" style="margin-top: -4px;">
        <button class="btn">🏠 Pages & Sources</button>
        <button class="btn">🌍 Languages & Timezone</button>
        <button class="btn">💻 Browser & Device</button>
      </div>
      <h4>Audience Distribution & Preferences</h4>
    </div>
    <div class="p-3">
      <div class="hstack">
        <div style="width: 33%">
          <div id="chart-browser-primary-languages" style="height: 200px;"></div>
          <p class="text-center">Browser Primary Languages</p>
        </div>
        <div style="width: 33%">
          <div id="chart-ip-timezone" style="height: 200px;"></div>
          <p class="text-center">IP Timezone</p>
        </div>
        <div style="width: 33%">
          <div id="chart-browser-timezone" style="height: 200px;"></div>
          <p class="text-center">Browser Timezone</p>
        </div>
      </div>
<!--      <h3>Browser & Device</h3>-->
<!--      <div class="hstack">-->
<!--        <div style="width: 50%">-->
<!--          <div id="chart-browser" style="height: 200px;"></div>-->
<!--          <p class="text-center">Browser</p>-->
<!--        </div>-->
<!--        <div style="width: 50%">-->
<!--          <div id="chart-operation-system" style="height: 200px;"></div>-->
<!--          <p class="text-center">Operation System</p>-->
<!--        </div>-->
<!--      </div>-->
<!--      <div class="hstack">-->
<!--        <div style="width: 50%">-->
<!--          <div id="chart-browser-vendors" style="height: 200px;"></div>-->
<!--          <p class="text-center">Browser Vendors</p>-->
<!--        </div>-->
<!--        <div style="width: 50%">-->
<!--          <div id="chart-device-platforms" style="height: 200px;"></div>-->
<!--          <p class="text-center">Device Platform</p>-->
<!--        </div>-->
<!--        <div style="width: 50%">-->
<!--          <div id="chart-screen-resolution" style="height: 200px;"></div>-->
<!--          <p class="text-center">Screen Resolution Levels</p>-->
<!--        </div>-->
<!--      </div>-->
<!--      <h3>Pages & Sources</h3>-->
<!--      <div class="hstack">-->
<!--        <div style="width: 70%">-->
<!--          <p class="text-center">Top Pages</p>-->
<!--          <div id="chart-top-pages" style="height: 200px;"></div>-->
<!--        </div>-->
<!--        <div style="width: 30%">-->
<!--          <div id="chart-top-referrers" style="height: 200px;"></div>-->
<!--          <p class="text-center">Source & Referrer</p>-->
<!--        </div>-->
<!--      </div>-->
    </div>
  </div>

</template>

<script>
export default {
  name: "StatisticsPanel"
}
</script>

<style scoped>

</style>