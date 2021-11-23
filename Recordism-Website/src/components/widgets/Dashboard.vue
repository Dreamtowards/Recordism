<template>
  <div class="thumbnail border-bottom-0 p-0" style="border-bottom-left-radius: 0; border-bottom-right-radius: 0;">
    <div class="p-3 border-bottom">
      <select class="btn w-auto float-end ms-2 px-2" style="margin-top: -4px;appearance: auto;" v-model="currentPanel">
        <option value="0">Geolocation Heatmap</option>
        <option value="1">Audience Visitations</option>
        <option value="2">Preferences Analysis</option>
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
        <div id="chart-audience-visitations" class="mt-4 me-4 ms-2 pb-1" style="height: 220px;"></div>
        <div class="d-block float-end me-3 btn-group" style="margin-top: -240px;">
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
        <audience-preferences-analysis></audience-preferences-analysis>
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



import "../../assets/css/c3.css";
import c3 from "../../assets/js/c3.js";


import AudiencePreferencesAnalysis from "./AudiencePreferencesAnalysis.vue";


export default {
  name: "Dashboard",
  components: {AudiencePreferencesAnalysis},
  data() {return {
    initializedPanels: [],
    showAdvancedSearch: false,
    presetSearchMode: 2,
    chartAudienceVisitations: undefined,
    audivistIntervalSec: 60*60,
    currentPanel: 0,
  }},
  methods: {

    switchPanel(idx) {
      let pb = this.$refs.panel_body;
      for (let child of pb.children) {
        child.style.display = 'none';
      }
      pb.children.item(idx).style.display = 'block';

      const initFunc = [
        this.setupGeolocationMap,
        this.setupAudienceVisitations,
        this.setupPreferencesAnalysis,
        alert,
        alert
      ];
      if (!this.initializedPanels.includes(idx)) {
        initFunc[idx]();
        this.initializedPanels.push(idx);
      }
    },

    setupGeolocationMap() {

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

        // let d = [
        //   {time: 1, visitors: 1, visits: 10},
        //   {time: 2, visitors: 1, visits: 10},
        //   {time: 3, visitors: 1, visits: 10}
        // ];
        // let row = [
        //     ['time', 'visits', 'visitors']
        // ];
        // for (const d of resp.sample) {
        //   row.push([d.begin_time-100000000000, d.visits, d.visitors]);
        // }

        this.chartAudienceVisitations.load({
          json: resp.sample,
          keys: {
            x: 'begin_time',
            value: ['visits', 'visitors']
          }
        })
      });
    },
    setupAudienceVisitations() {

      this.chartAudienceVisitations = c3.generate({
        bindto: '#chart-audience-visitations',
        data: {
          // x: 'begin_time',
          // xFormat: null,
          // rows: [['time']],
          json: [],
          keys: {
            x: 'begin_time',
            value: []
          },
          types: {
            // visitors: 'line',
            // visits: 'line'
          }
        },
        axis: {
          x: {
            type: 'timeseries',
            tick: {
              format: '%Y-%m-%d\n%H:%M:%S',
              // count: 2,
              rotate: 0,
            },
          },
          y: {
            // tick: {count: 5},
            // padding: { top: 0, bottom: 0,},
          }
        },
        zoom: {
          enabled: true,
          rescale: false
        },
        grid: {
          y: {show: true}
        },
        // subchart: {
        //   show: true
        // }
      });

      this.requestSiteAudienceVisitations();

    },





  },
  mounted() {



    this.currentPanel = 2;
  },
  watch: {
    currentPanel(to) {

      this.switchPanel(to);
    },
    prefanalUseSubchart(to) {

      if (to)
        this.chartPreferencesAnalysis.subchart.show();
      else
        this.chartPreferencesAnalysis.subchart.hide();
    }
  }
}
</script>

<style scoped>


</style>