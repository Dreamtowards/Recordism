<template>
<div>
  <div id="chart-preferences-analysis" class="mt-3 me-4 ms-2 pb-2" style="height: 320px;"></div>
  <div class="d-block float-end me-3" style="margin-top: -335px;position: relative;">
    <button class="btn align-top" :class="{'active': useSubchart}" @click="useSubchart=!useSubchart">Subchart</button>

    <select style="width: 100px;" class="btn align-top" title="Interval" @change="requestSitePreferencesAnalysis" v-model="intervalSec">
      <option :value="60*60">1 hour</option>
      <option :value="60*60*8">8 hours</option>
      <option :value="60*60*24">1 day</option>
      <option :value="60*60*24*7">7 days</option>
      <option :value="60*60*24*30">1 month</option>
      <option :value="60*60*24*30*3">3 months</option>
      <option :value="60*60*24*30*12">1 year</option>
    </select>
    <select style="width: auto;" class="btn align-top" title="Counting" v-model="selCounting" @change="requestSitePreferencesAnalysis">
      <option value="visits">Visits</option>
      <option value="visitors">Visitors</option>
    </select>
    <select class="btn align-top" style="width: auto;" title="Category" v-model="selCategory" @change="requestSitePreferencesAnalysis">
      <option value="country">Country</option>
      <option value="region">Region</option>
      <option value="city">City</option>
      <option value="isp">ISP</option>

      <option value="browser_timezone">Browser Timezone</option>
      <option value="browser_language">Browser Language</option>
      <option value="ip_timezone">IP Timezone</option>

      <option value="browser_vendor">Browser Vendor</option>
      <option value="device_platform">Device Platform</option>
      <option value="operation_system">Operation System</option>
      <option value="browser_model">Browser</option>

      <option value="window_title">Title</option>
      <option value="window_url">URL</option>
      <option value="window_referrer_url">Referrer URL</option>

<!--      <option>IP</option>-->
<!--      <option>CookieID</option>-->
    </select>
  </div>
</div>
</template>

<script>

// for Visitors, is by IP, or by Cookie ID.?
// default should be by IP, it's traditional way, also be most-expected behavior.
// Cookie ID is kind-of-radical way, may not expected. also may not really accurate.


import {request} from "../../main";
import c3 from "../../assets/js/c3";

export default {
  name: "AudiencePreferencesAnalysis",
  data() {return {
    intervalSec: 60*60,
    useSubchart: false,
    theChart: undefined,
    selCounting: 'visits',
    selCategory: 'country',
    lastRowHead: [],
  }},
  methods: {

    requestSitePreferencesAnalysis() {

      request("/site_preferences_analysis", {
        interval: 1000*this.intervalSec,
        interval_slices_limit: 20,
        counting: this.selCounting,
        category: this.selCategory,
        category_items_limit: 12
      }, resp => {
        let samps = resp.sample;

        let rowhead = ['time'];
        for (const keyTime in samps) {
          for (const item of samps[keyTime]) {
            if (!rowhead.includes(item.item))
              rowhead.push(item.item);
          }
        }

        let rows = [rowhead];
        for (const keyTime in samps) {

          let r = [];
          for (let i in rowhead)
            r[i] = 0;

          r[0] = parseInt(keyTime);
          for (const item of samps[keyTime]) {
            r[rowhead.indexOf(item.item)] = item.count;
          }
          rows.push(r);
        }

        let typesAllArea = {};
        for (let i = 1; i < rowhead.length; i++) {
          typesAllArea[rowhead[i]] = 'area';
        }

        this.theChart.load({
          rows: rows,
          types: typesAllArea,
          unload: this.lastRowHead
        });

        this.lastRowHead = rowhead;
        this.theChart.groups([rowhead.slice(1)]);
      });
    },
    setup() {

      this.theChart = c3.generate({
        bindto: '#chart-preferences-analysis',
        data: {
          x: 'time',
          xFormat: null,
          rows: [['time']],
          // groups: [rowhead.slice(1)],
          // types: typesAllArea,
        },
        axis: {
          x: {
            type: 'timeseries',
            tick: {
              format: '%Y-%m-%d %H:%M:%S',
              multiline: true,
              outer: true,
              culling: {
                max: 1
              }
            },
          },
          y: {/* tick: { count: 5, culling: { max: 5 } }, padding: { top: 0, bottom: 0,}, */}
        },
        zoom: {
          enabled: true,
          rescale: false
        },
        grid: {
          y: {show: true}
        },
        // subchart: { show: true }
      });


      this.requestSitePreferencesAnalysis();

    }

  },
  mounted() {


  },
  watch: {

    useSubchart(to) {

      if (to)
        this.theChart.subchart.show();
      else
        this.theChart.subchart.hide();
    }
  }
}
</script>

<style scoped>

</style>