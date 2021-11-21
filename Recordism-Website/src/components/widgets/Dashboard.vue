<template>
  <div class="thumbnail border-bottom-0 p-0" style="border-bottom-left-radius: 0; border-bottom-right-radius: 0;">
    <div class="p-3 border-bottom">
      <select class="btn w-auto float-end ms-2 px-2" style="margin-top: -4px;appearance: auto;" @change="switchPanel($event.target.selectedIndex)">
        <option>Audience Map</option>
        <option>Overview</option>
<!--        <option>Geolocation</option>-->
        <option title="Pages, Referrers">Pages & Sources</option>
        <option title="Browser Primary Language, Browser Timezone, IP Timezone">Language & Timezone</option>
        <option title="Browser, OS, Device Platform, Browser Vendor, Screen, ISP">Browser & Device</option>
      </select>
      <div class="btn-group d-inline-block float-end" data-toggle="buttons-radio" style="margin-top: -4px;">
        <button class="btn"><span class="text-danger" style="font-size: 80%;">◉</span> Real time</button>
        <button class="btn active">Hour</button>
        <button class="btn">Day</button>
        <button class="btn">Week</button>
        <button class="btn">Month</button>
        <button class="btn" :class="{'active': showAdvancedSearch}" @click="showAdvancedSearch = !showAdvancedSearch">
          {{ showAdvancedSearch ? 'Less' : '...' }}
          <span v-if="showAdvancedSearch" class="caret" style="transform: rotate(180deg)"></span>
        </button>
        <!--          <button class="btn">🕰 Custom</button>-->
      </div>
      <h4>Audience Overview</h4>
<!--      <small class="muted d-block" style="margin-top: 2px;">{{currentPanel}}</small>-->

      <div class="well mt-3 mb-0" v-show="showAdvancedSearch">
        <h5>Advanced Search</h5>
        <hr class="mt-2">
        <div>
          <input type="text" style="width: 110px;" placeholder="IP">
          <input type="text" style="width: 90px;" placeholder="Country">
          <input type="text" style="width: 90px;" placeholder="Region">
          <input type="text" style="width: 90px;" placeholder="City">
          <input type="text" style="width: 90px;" placeholder="IP Timezone">
          <div class="vr mx-1 pt-1"></div>
          <input type="text" style="width: 120px;" placeholder="Cookie ID">
        </div>
        <div>
          <input type="text" style="width: 190px;" placeholder="User Agent">
          <input type="text" style="width: 90px;" placeholder="Language">
          <input type="text" style="width: 120px;" placeholder="Browser Timezone">
          <input type="text" style="width: 120px;" placeholder="Browser Vendor">
          <input type="text" style="width: 120px;" placeholder="Device Platform">
          <input type="text" style="width: 120px;" placeholder="Screen Resolution+">
        </div>
        <div>
          <input type="text" style="width: 180px;" placeholder="Title">
          <input type="text" style="width: 200px;" placeholder="URL">
          <div class="vr mx-1 pt-1"></div>
          <input type="text" style="width: 180px;" placeholder="Referrer URL">
        </div>
        <div>
          <input type="text" style="width: 120px;" placeholder="After">
          <input type="text" style="width: 120px;" placeholder="Before">
          <input type="text" style="width: 110px;" placeholder="Duration+">
          <button class="btn btn-primary float-end px-4 mt-2">Search</button>
        </div>

<!--        <div style="height: 24px;">-->
<!--        </div>-->
      </div>
    </div>
    <div ref="panel_body">
      <div>
        <div id="map-main" style="height: 430px;"></div>
      </div>
      <div>
        <div id="chart-overview" class="mt-3" style="height: 220px;"></div>
      </div>
      <div>
        <div id="chart-pages" class="mt-3" style="height: 320px;"></div>
      </div>
      <div>
        Language and Timezone
      </div>
      <div>
        Device
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Dashboard",
  data() {return {
    initializedPanels: [],
    showAdvancedSearch: false
  }},
  methods: {

    switchPanel(idx) {
      let pb = this.$refs.panel_body;
      for (let child of pb.children) {
        child.style.display = 'none';
      }
      pb.children.item(idx).style.display = 'block';

      const initFunc = [
          this.setupAudienceMap,
          this.setupChartAccessOverview,
          this.setupChartPages,
          alert,
          alert
      ];
      if (!this.initializedPanels.includes(idx)) {
        initFunc[idx]();
        this.initializedPanels.push(idx);
      }
    },

    setupChartAccessOverview() {

      let data = [ ];
      let ChartLine = new Morris.Line({
        element: 'chart-overview',
        data: data,
        xkey: 'time',
        ykeys: ['views', 'viewers', 'online_viewers', 'online_pages'],
        labels: ['Page Views', 'Unique Viewers', 'Online Viewers', 'Online Pages'],
        hideHover: 'auto',
        lineColors: ['#62ff37', '#67d029', '#fc4040', '#a82e2e'],
        lineWidth: [2.5, 1.5, 2.5, 1.5]
      });

      let lastUpdate = 0;
      setInterval(() => {
        if (Date.now() - lastUpdate > (data.length < 10 ? 1 : 6000)) {
          lastUpdate = Date.now();
          data.push({
            time: Date.now(),
            views: (100 * Math.random()).toFixed(0),
            viewers: (10 * Math.random()).toFixed(0),
            online_viewers: (130 * Math.random()).toFixed(0),
            online_pages: (180 * Math.random()).toFixed(0),
          });
          ChartLine.setData(data);
        }
      }, 1800);

    },

    setupChartPages() {

      new Morris.Area({
        element: 'chart-pages',
        data: [
          {time: '1000', page1: 30, page2: 60},
          {time: '1100', page1: 40, page2: 90},
          {time: '1300', page1: 80, page2: 40}
        ],
        xkey: 'time',
        ykeys: ['page1', 'page2'],
        labels: ['Page1', 'Page2'],
        hideHover: 'auto',
      });

    },

    setupAudienceMap() {

      var lOSM = new L.TileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {});
      var lMapbox = L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoiY2x1c3RybWFwcyIsImEiOiJjaXEwbG9hZXowMDByaHJuZDU0dDU0cDZ0In0.RDTRO6skanUfOnb6eijYEA', {});
      var lOpenMap = L.tileLayer('http://korona.geog.uni-heidelberg.de/tiles/roads/x={x}&y={y}&z={z}', {});
      var lWSM = L.tileLayer('//services.arcgisonline.com/arcgis/rest/services/World_Street_Map/MapServer/tile/{z}/{y}/{x}', {});

      var littleton = L.marker([39.61, -105.02]).bindPopup('This is Littleton, CO.'),
          denver    = L.marker([39.74, -104.99]).bindTooltip('This is Denver, CO.'),
          aurora    = L.marker([39.73, -104.8]).bindTooltip('This is Aurora, CO.'),
          golden    = L.marker([39.77, -105.23]).bindTooltip('This is Golden, CO.');
      let mlCities = L.layerGroup([littleton, denver, aurora, golden]);

      let pmark =
          L.circleMarker([50.5, -0.09], {
            radius: 8,
            fillColor: "#FF0000",
            fillOpacity: .7,
            weight: .5,
            color: "#ffffff",
            className: "mn"
          }).bindTooltip('ABC');
      let mlUsrs = L.layerGroup([pmark]);

      var map = L.map('map-main', {
        center: [19.426216, 15.716648],
        zoom: 1.9,
        scrollWheelZoom: false,
        tap: false,
        layers: [lOSM, mlUsrs]
      });

      // L.control.scale().addTo(map);
      // L.control.attribution({prefix: "abc"}).addTo(map);

      // L.marker([51.5, 50 * Math.random()]).addTo(map)
      //     .bindPopup('A pretty CSS3 popup.<br> Easily customizable.');

      L.control.layers({
        "WorldStreetMap": lWSM,
        "OpenMap": lOpenMap,
        "OpenStreetMap": lOSM,
        "MapBox": lMapbox
      }, {
        "Cities": mlCities,
        "Markers": mlUsrs
      }).addTo(map);

    }
  },
  mounted() {

    // this.setupAudienceMap();
    //
    // this.setupChartAccessOverview();
    //
    // this.setupChartPages();

    this.switchPanel(0);
  }
}
</script>

<style scoped>


</style>