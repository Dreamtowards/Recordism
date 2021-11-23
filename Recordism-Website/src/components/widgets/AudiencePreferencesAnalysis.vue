<template>
<div>
  <div id="chart-preferences-analysis" class="mt-3 me-4 ms-2 pb-2" style="height: 320px;"></div>
  <div class="d-block float-end me-3" style="margin-top: -335px;position: relative;">
    <button class="btn align-top" :class="{'active': useSubchart}" @click="useSubchart=!useSubchart">Subchart</button>
    <div class="d-inline-block btn-group">
      <button class="btn">Visits</button>
      <button class="btn">Visitors</button>
    </div>
    <select style="width: 100px;" class="btn align-top" title="Interval" @change="requestSitePreferencesAnalysis" v-model="intervalSec">
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
</template>

<script>
import {request} from "../../main";
import c3 from "../../assets/js/c3";

export default {
  name: "AudiencePreferencesAnalysis",
  data() {return {
    intervalSec: 60*60,
    useSubchart: false,
    theChart: undefined,
  }},
  methods: {


    requestSitePreferencesAnalysis() {

      request("/site_preferences_analysis", {
        interval: 1000*this.prefanalIntervalSec
      }, resp => {

        // let data = [];
        // let keys = [];
        //
        // for (let samp of resp.sample) {
        //   let dataI = -1;  // existed.
        //   for (let i in data) {
        //     if (data[i].time === samp.begin_time) {
        //       dataI = i;
        //       break;
        //     }
        //   }
        //   if (!keys.includes(samp.field)) {
        //     keys.push(samp.field);
        //   }
        //
        //   if (dataI !== -1) {
        //     data[dataI][samp.field] = samp.visits;
        //   } else {
        //     let v = {};
        //     v.time = samp.begin_time;
        //     v[samp.field] = samp.visits;
        //     data.push(v);
        //   }
        // }

        let d = resp.sample;

        let rowhead = ['time'];

        for (const p of d) {
          if (!rowhead.includes(p.field)) {
            rowhead.push(p.field);
          }
        }
        let rows = [rowhead];
        for (const p of d) {
          let foundrow = false;
          for (const row of rows) {
            if (row[0] === p.begin_time) {
              row[rowhead.indexOf(p.field)] = p.visits;
              foundrow = true;
              break;
            }
          }

          if (!foundrow) {
            let row = [];
            for (let i = 0; i < rowhead.length; i++) {
              row[i] = 0;
            }
            row[0] = p.begin_time;
            row[rowhead.indexOf(p.field)] = p.visits;
            rows.push(row);
          }
        }

        let typesAllArea = {};
        for (let i = 1; i < rowhead.length; i++) {
          typesAllArea[rowhead[i]] = 'area';
        }

        this.chartPreferencesAnalysis.load({
          rows: rows,
          types: typesAllArea
        });

        this.chartPreferencesAnalysis.groups([rowhead.slice(1)]);
      });
    },
    setup() {

      this.chartPreferencesAnalysis = c3.generate({
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

  }
}
</script>

<style scoped>

</style>