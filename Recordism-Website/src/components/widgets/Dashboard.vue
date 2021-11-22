<template>
  <div class="thumbnail border-bottom-0 p-0" style="border-bottom-left-radius: 0; border-bottom-right-radius: 0;">
    <div class="p-3 border-bottom">
      <select class="btn w-auto float-end ms-2 px-2" style="margin-top: -4px;appearance: auto;" @change="switchPanel($event.target.selectedIndex)">
        <option>Geolocation Heatmap</option>
        <option>Audience Visitations</option>
        <option>Preferences Analysis</option>
<!--        <option>Geolocation</option>-->
<!--        <option title="Pages, Referrers">Pages & Sources</option>-->
<!--        <option title="Browser Primary Language, Browser Timezone, IP Timezone">Language & Timezone</option>-->
<!--        <option title="Browser, OS, Device Platform, Browser Vendor, Screen, ISP">Browser & Device</option>-->
      </select>
      <div class="btn-group d-inline-block float-end" data-toggle="buttons-radio" style="margin-top: -4px;">
        <button class="btn" :class="{'active': presetSearchMode===1}" @click="presetSearchMode=1"><span class="text-danger" style="font-size: 80%;">◉</span> Real time</button>
        <button class="btn" :class="{'active': presetSearchMode===2}" @click="presetSearchMode=2">Day</button>
        <button class="btn" :class="{'active': presetSearchMode===3}" @click="presetSearchMode=3">Week</button>
        <button class="btn" :class="{'active': presetSearchMode===4}" @click="presetSearchMode=4">Month</button>
        <button class="btn" :class="{'active': presetSearchMode===5}" @click="presetSearchMode=5">Year</button>
        <button class="btn" :class="{'active': presetSearchMode===0}" @click="presetSearchMode=0">
          ...<!--<span class="caret" ></span>-->
        </button>
        <!--          <button class="btn">🕰 Custom</button>-->
      </div>
      <h4>Audience Overview</h4>
<!--      <small class="muted d-block" style="margin-top: 2px;">{{currentPanel}}</small>-->

      <div class="well mt-3 mb-0" v-show="presetSearchMode===0">
        <h5>Advanced Condition</h5>
        <hr class="mt-2">
        <div>
          <input type="text" style="width: 230px;" placeholder="Access ID">
          <input type="text" style="width: 110px;" placeholder="IP">
          <input type="text" style="width: 140px;" placeholder="Cookie ID">
          <div class="vr mx-1 pt-1"></div>
          <input type="text" style="width: 110px;" placeholder="Event Type">
          <input type="text" style="width: 170px;" placeholder="Event Data">
        </div>
        <div>
          <input type="text" style="width: 90px;" placeholder="Country">
          <input type="text" style="width: 90px;" placeholder="Region">
          <input type="text" style="width: 90px;" placeholder="City">
          <input type="text" style="width: 90px;" placeholder="IP Timezone">
          <input type="text" style="width: 80px;" placeholder="ISP">
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

      </div>
    </div>
    <div ref="panel_body">
      <div>
        <div id="map-main" style="height: 430px;"></div>
<!--        <div class="d-block float-end" style="z-index: 100700;position: relative;margin-top: -420px;margin-right: 70px;">-->
<!--          <div class="btn-group d-inline-block">-->
<!--            <button class="btn active">Visits</button>-->
<!--            <button class="btn">Visitors</button>-->
<!--          </div>-->
<!--        </div>-->
      </div>
      <div>
        <div id="chart-overview" class="mt-3" style="height: 220px;"></div>
        <div class="d-block float-end me-3 btn-group" style="margin-top: -226px;">
          <select style="width: 100px;" title="Interval" @change="requestSiteAudienceVisitations" v-model="audivistIntervalSec">
            <option :value="60*60">1 hour</option>
            <option :value="60*60*8">8 hours</option>
            <option :value="60*60*24">1 day</option>
            <option :value="60*60*24*7">7 days</option>
            <option :value="60*60*24*30">1 month</option>
            <option :value="60*60*24*30*3">3 months</option>
            <option :value="60*60*24*30*12">1 year</option>
          </select>
        </div>
      </div>
      <div>
        <div id="chart-pages" class="mt-3" style="height: 320px;"></div>
        <div class="d-block float-end me-3" style="margin-top: -330px;position: relative;">
          <div class="d-inline-block btn-group">
            <button class="btn">Visits</button>
            <button class="btn">Visitors</button>
          </div>
          <select style="width: 100px;" class="btn align-top" title="Interval" @change="requestSitePreferencesAnalysis" v-model="prefanalIntervalSec">
            <option :value="60*60">1 hour</option>
            <option :value="60*60*8">8 hours</option>
            <option :value="60*60*24">1 day</option>
            <option :value="60*60*24*7">7 days</option>
            <option :value="60*60*24*30">1 month</option>
            <option :value="60*60*24*30*3">3 months</option>
            <option :value="60*60*24*30*12">1 year</option>
          </select>
          <select class="btn align-top" style="width: auto;">
            <option>Country</option>
            <option>Region</option>
            <option>City</option>
            <option>IP Timezone</option>
            <option>ISP</option>
            <option>Browser Vendor</option>
            <option>Browser Timezone</option>
            <option>Browser Language</option>
            <option>Device Platform</option>
            <option>Operation System</option>
            <option>Browser</option>
            <option>Title</option>
            <option>URL</option>
            <option>Referrer URL</option>
            <option>IP</option>
            <option>CookieID</option>
          </select>
        </div>
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

import {request} from "../../main";

import {utTextDurationTimeText} from "../../ut_parse";
import IntervalInput from "./IntervalInput.vue";

export default {
  name: "Dashboard",
  components: {IntervalInput},
  data() {return {
    initializedPanels: [],
    showAdvancedSearch: false,
    presetSearchMode: 2,
    uiChartVisitsLine: undefined,
    uiChartPrefAnalysis: undefined,
    prefanalIntervalSec: 60*60,
    audivistIntervalSec: 60*60,
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

    setupAudienceMap() {

      var ltOSM = new L.TileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {});
      var ltMapbox = L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoiY2x1c3RybWFwcyIsImEiOiJjaXEwbG9hZXowMDByaHJuZDU0dDU0cDZ0In0.RDTRO6skanUfOnb6eijYEA', {});
      var ltOpenMap = L.tileLayer('http://korona.geog.uni-heidelberg.de/tiles/roads/x={x}&y={y}&z={z}', {});
      var ltWSM = L.tileLayer('//services.arcgisonline.com/arcgis/rest/services/World_Street_Map/MapServer/tile/{z}/{y}/{x}', {});

      let lmVisits = L.layerGroup([]);
      let lmVisitors = L.layerGroup([]);

      var map = L.map('map-main', {
        center: [19.426216, 15.716648],
        zoom: 1.9,
        scrollWheelZoom: false,
        tap: false,
        layers: [ltOSM, lmVisits, lmVisitors]
      });

      // L.control.scale().addTo(map);
      // L.control.attribution({prefix: "abc"}).addTo(map);
      // L.marker([51.5, 50 * Math.random()]).addTo(map)
      //     .bindPopup('A pretty CSS3 popup.<br> Easily customizable.');

      L.control.layers({
        "WorldStreetMap": ltWSM,
        "OpenMap": ltOpenMap,
        "OpenStreetMap": ltOSM,
        "MapBox": ltMapbox
      }, {
        "Visits": lmVisits,
        "Visitors": lmVisitors
      }).addTo(map);

      request("/site_heatmap", {}, resp => {

        for (let h of resp.heatmap) {

          L.circleMarker([h.lat, h.lon], {
            radius: h.visits * 3,  // ratio
            fillColor: "#b2b2b2",
            fillOpacity: .7,
            weight: .5,
            color: "#ffffff",
            className: "mn"
          }).bindTooltip(`${h.visits} visits from ${h.city}, ${h.country}`).addTo(lmVisits);

          const HoursHalf = 30 * 60 * 1000;
          L.circleMarker([h.lat, h.lon], {
            radius: h.visitors * 3,  // ratio
            fillColor: (Date.now() - h.last_visit_time) < HoursHalf ? "#ffae00" : "#FF0000",
            fillOpacity: .7,
            weight: .5,
            color: "#ffffff",
            className: "mn"
          }).bindTooltip(`${h.visitors} visitors from ${h.city}, ${h.country}. <small class="muted" style="font-size: 80%;">${utTextDurationTimeText((Date.now() - h.last_visit_time)/1000)} ago.</small>`).addTo(lmVisitors);
        }
      });

    },

    requestSiteAudienceVisitations() {

      request("/site_audience_visitations", {
        interval: 1000*this.audivistIntervalSec
      }, resp => {

        this.uiChartVisitsLine.setData(resp.sample);
      });
    },
    setupChartAccessOverview() {

      this.uiChartVisitsLine = new Morris.Line({
        element: 'chart-overview',
        data: [],
        xkey: 'begin_time',
        ykeys: ['visits', 'visitors'],//, 'online_viewers', 'online_pages'],
        labels: ['Page Views', 'Unique Viewers'],//, 'Online Viewers', 'Online Pages'],
        hideHover: 'auto',
        lineColors: ['#62ff37', '#67d029'],//, '#fc4040', '#a82e2e'],
        lineWidth: [2.5, 1.5],//, 2.5, 1.5]
      });

      this.requestSiteAudienceVisitations();

    },

    requestSitePreferencesAnalysis() {

      request("/site_preferences_analysis", {
            interval: 1000*this.prefanalIntervalSec
          }, resp => {

        let data = [];
        let keys = [];

        for (let samp of resp.sample) {
          let dataI = -1;  // existed.
          for (let i in data) {
            if (data[i].time === samp.begin_time) {
              dataI = i;
              break;
            }
          }
          if (!keys.includes(samp.field)) {
            keys.push(samp.field);
          }

          if (dataI !== -1) {
            data[dataI][samp.field] = samp.visits;
          } else {
            let v = {};
            v.time = samp.begin_time;
            v[samp.field] = samp.visits;
            data.push(v);
          }
        }

        // if (this.uiChartPrefAnalysis)
        //     this.uiChartPrefAnalysis.remove();
        this.uiChartPrefAnalysis = new Morris.Area({
          element: 'chart-pages',
          data: data,
          xkey: 'time',
          ykeys: keys,
          labels: keys,
          hideHover: 'auto',
        });

      });
    },
    setupChartPages() {



      this.requestSitePreferencesAnalysis();

        // new Morris.Area({
        //   element: 'chart-pages',
        //   data: [
        //     {time: '1000', page1: 30, page2: 60},
        //     {time: '1100', page1: 40},
        //     {time: '1300', page1: 80, page2: 40}
        //   ],
        //   xkey: 'time',
        //   ykeys: ['page1', 'page2'],
        //   labels: ['Z1', 'Page2'],
        //   hideHover: 'auto',
        // });

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