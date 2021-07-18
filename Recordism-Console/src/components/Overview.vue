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
      <table class="ui single line table">
        <thead>
          <tr>
            <th>Idx</th>
<!--            <th>Time / AXID</th>-->
            <th title="IP Location.">Location</th>
            <th>Duration</th>
            <th title="User Browser Fingerprint\nBrowser Cookie User ID">FP / CUID</th>
            <th>BTZ/BLG</th>
            <th>Browser</th>
            <th>OS</th>
            <th>Screen BH</th>
            <th>IP</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(access_item, idx) in access_list">
<!--            <td>-->
<!--              <small>{{ utTextTimeYMD(access_item.time) }}</small> {{ utTextTimeHMAPM(access_item.time) }} <br>-->
<!--              <small class="color-text-tertiary">{{access_item.access_id.substr(0,8)}}..</small></td>-->

<!--            <td>-->
<!--              <span style="font-size: 18px;margin-right: 6px;">{{utGetFlagEmoji(access_item.ip_info.country_code)}}</span>-->
<!--              <span style="vertical-align: text-bottom;">{{access_item.ip_info.city}}, {{access_item.ip_info.country}}</span></td>-->

<!--            <td>-->
<!--              {{ utTextDurationTimeHMS((access_item.time_last_keepalive - access_item.time)/1000) }}-->
<!--              <span v-if="Date.now() - access_item.time_last_keepalive < 20000" style="color: green" title="Online">*</span></td>-->

<!--            <td>-->
<!--              <span class="code" :style="{color: utHexColorHashString(access_item.browser_fingerprint)}">{{ access_item.browser_fingerprint.substr(0, 8) }}</span>...<br>-->
<!--              <span class="code" :style="{color: utHexColorHashString(access_item.browser_cookie_uid)}">{{ access_item.browser_cookie_uid.substr(0, 8) }}</span>...</td>-->

<!--            <td>-->
<!--              {{ access_item.browser_timezone }}<br>-->
<!--              <small class="color-text-tertiary">-->
<!--                [{{access_item.browser_language}}]-->
<!--                {{access_item.browser_languages.replace(access_item.browser_language, "").replaceAll(","," ").trim()}}</small></td>-->

            <td>{{ idx }}</td>

            <td :title="access_item.user_agent">
              <span>{{ (access_item.ua_info = utParseUA(access_item.user_agent)).browser.name }}</span>
              <br><span style="font-size: 10px;" class="color-text-tertiary">{{ utSimpleVersion(access_item.ua_info.browser.version, 2) }}</span></td>

            <td>
              {{ access_item.ua_info.os.name }}<br>
              <span style="font-size: 10px;">
                {{ access_item.ua_info.os.arch }}{{ (access_item.ua_info.os.arch&&access_item.ua_info.os.version)?", ":"" }}{{ access_item.ua_info.os.version?utSimpleVersion(access_item.ua_info.os.version,2):"" }}
              </span></td>

            <td><small>{{access_item.user_agent}}</small></td>

<!--            <td>2K<sup>2560x1440</sup><br>B/80%</td>-->
<!--            <td>1.42.51.148</td>-->
          </tr>
        </tbody>
      </table>
    </div>
    <a class="ui button" @click="load_access_list">Refresh</a>

    <br><br><br>
    <p style="font-size: 12px;color: #888;">© Copyright 2021 <a href="#">Recordism</a> | <a href="#">Privacy Policy</a></p>
  </div>

</div>
</template>

<script>

import {request, utGetFlagEmoji, utTextDurationTimeHMS, utTextTimeYMD, utTextTimeHMAPM, utHexColorHashString, utParseUA, utSimpleVersion} from "../main.js";

export default {
  name: "Overview",
  data() {return{
    access_list: [{
      user_agent: "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36"
    }]
  }},
  methods: {
    load_access_list() {
      // request("/api/access_list", {site_id: 1}, resp => {
      //   this.access_list = resp;
      // });
      let s = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36\n" +
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/600.7.12 (KHTML, like Gecko) Version/8.0.7 Safari/600.7.12\n" +
          "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36\n" +
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/600.8.9 (KHTML, like Gecko) Version/8.0.8 Safari/600.8.9\n" +
          "Mozilla/5.0 (iPhone; CPU iPhone OS 8_2 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) CriOS/44.0.2403.67 Mobile/12D508 Safari/600.1.4\n" +
          "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36\n" +
          "Mozilla/5.0 (Windows NT 6.1; rv:38.0) Gecko/20100101 Firefox/38.0\n" +
          "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36\n" +
          "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10240\n" +
          "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36\n" +
          "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/42.0 Safari/537.31\n" +
          "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0\n" +
          "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)\n" +
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.59.10 (KHTML, like Gecko) Version/5.1.9 Safari/534.59.10\n" +
          "Mozilla/5.0 (compatible; Yahoo! Slurp; http://help.yahoo.com/help/us/ysearch/slurp)\n" +
          "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)\n" +
          "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) coc_coc_browser/50.0.125 Chrome/44.0.2403.125 Safari/537.36\n" +
          "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)\n" +
          "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0\n" +
          "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36 Edge/12.0\n" +
          "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0\n" +
          "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; GTB7.5; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C)\n" +
          "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10240\n" +
          "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko\n" +
          "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0) LinkCheck by Siteimprove.com\n" +
          "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36\n" +
          "Mozilla/5.0 (Windows NT 6.3; Win64; x64; Trident/7.0; rv:11.0) like Gecko\n" +
          "Mozilla/5.0 (iPhone; CPU iPhone OS 8_4 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12H143 Safari/600.1.4\n" +
          "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36\n" +
          "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0\n" +
          "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36\n" +
          "Mozilla/5.0 (Windows NT 6.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36\n" +
          "Mozilla/5.0 (BB10; Touch) AppleWebKit/537.35+ (KHTML, like Gecko) Version/10.3.2.2339 Mobile Safari/537.35+\n" +
          "Mozilla/5.0 (Windows NT 6.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36\n" +
          "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko\n" +
          "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0\n" +
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:40.0) Gecko/20100101 Firefox/40.0\n" +
          "Mozilla/5.0 (Mobile; Windows Phone 8.1; Android 4.0; ARM; Trident/7.0; Touch; rv:11.0; IEMobile/11.0; NOKIA; Lumia 525) like iPhone OS 7_0_3 Mac OS X AppleWebKit/537 (KHTML, like Gecko) Mobile Safari/537\n" +
          "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.107 Safari/537.36\n" +
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:37.0) Gecko/20100101 Firefox/37.0\n" +
          "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/44.0.2403.89 Chrome/44.0.2403.89 Safari/537.36\n" +
          "Mozilla/5.0 (iPad; CPU OS 8_4 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12H143 Safari/600.1.4\n" +
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10) AppleWebKit/600.1.25 (KHTML, like Gecko) QuickLook/5.0\n" +
          "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:40.0) Gecko/20100101 Firefox/40.0\n" +
          "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; MATBJS; rv:11.0) like Gecko\n" +
          "Mozilla/5.0 (Windows NT 6.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36\n" +
          "Mozilla/5.0 (Windows NT 10.0; Trident/7.0; rv:11.0) like Gecko\n" +
          "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)\n" +
          "Mozilla/5.0 (Windows NT 6.0; WOW64; rv:35.0) Gecko/20100101 Firefox/35.0\n" +
          "Mozilla/5.0 (Linux; U; Android 4.3; en-us; ZTE-Z667G Build/JLS36C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30\n" +
          "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36\n" +
          "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; Touch; ASU2JS; rv:11.0) like Gecko\n" +
          "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0\n" +
          "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko\n" +
          "Mozilla/5.0 (Windows NT 5.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36\n" +
          "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36\n" +
          "Mozilla/5.0 (iPad; CPU OS 8_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12F69 Safari/600.1.4\n" +
          "Mozilla/5.0 (Windows NT 6.0; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0\n" +
          "Mozilla/5.0 (iPhone; CPU iPhone OS 8_4_1 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12H321 Safari/600.1.4\n" +
          "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; TNJB; rv:11.0) like Gecko\n" +
          "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:41.0) Gecko/20100101 Firefox/41.0\n" +
          "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.2)\n" +
          "Mozilla/5.0 (Windows NT 6.1; Trident/7.0; rv:11.0) like Gecko\n" +
          "Mozilla/5.0 (iPad; CPU OS 7_1_2 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Version/7.0 Mobile/11D257 Safari/9537.53\n" +
          "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36\n" +
          "Mozilla/5.0 (iPhone; CPU iPhone OS 8_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12F70 Safari/600.1.4\n" +
          "Mozilla/5.0 (X11; Linux x86_64; rv:36.0) Gecko/20100101 Firefox/36.0\n" +
          "Mozilla/5.0 (iPad; CPU OS 8_4_1 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12H321 Safari/600.1.4\n" +
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:21.0) Gecko/20100101 Firefox/21.0\n" +
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:35.0) Gecko/20100101 Firefox/35.0\n" +
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.78.2 (KHTML, like Gecko) Version/6.1.6 Safari/537.78.2\n" +
          "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.130 Safari/537.36\n" +
          "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)\n" +
          "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0\n" +
          "Mozilla/5.0 (Mobile; rv:32.0) Gecko/32.0 Firefox/32.0\n" +
          "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.2; WOW64; Trident/7.0; .NET4.0E; .NET4.0C; .NET CLR 3.5.30729; .NET CLR 2.0.50727; .NET CLR 3.0.30729; Tablet PC 2.0; McAfee; AmericasCardroom; BRI/2; GWX:DOWNLOADED)\n" +
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36\n" +
          "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/7.0)\n" +
          "Mozilla/5.0 (iPad; CPU OS 8_2 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12D508 Safari/600.1.4\n" +
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2483.0 Safari/537.36\n" +
          "Mozilla/5.0 (Windows NT 5.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0\n" +
          "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/6.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E; MS-RTC LM 8)\n" +
          "Mozilla/5.0 (Android; Linux armv7l; rv:5.0) Gecko/20110615 Firefox/5.0 Fennec/5.0\n" +
          "Mozilla/5.0 (iPhone; CPU iPhone OS 8_1_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12B466 [FBAN/FBIOS;FBAV/37.0.0.21.273;FBBV/13822349;FBDV/iPhone6,1;FBMD/iPhone;FBSN/iPhone OS;FBSV/8.1.3;FBSS/2; FBCR/fido;FBID/phone;FBLC/fr_FR;FBOP/5]\n" +
          "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:40.0) Gecko/20100101 Firefox/40.0 Cyberfox/40.0\n" +
          "Mozilla/5.0 (iPad; CPU OS 7_0_4 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Mercury/8.7 Mobile/11B554a Safari/9537.53\n" +
          "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.3; WOW64; Trident/7.0; .NET4.0E; .NET4.0C; .NET CLR 3.5.30729; .NET CLR 2.0.50727; .NET CLR 3.0.30729; GWX:QUALIFIED; MASMJS)\n" +
          "Mozilla/5.0 (iPhone; CPU iPhone OS 7_1 like Mac OS X) AppleWebKit (KHTML, like Gecko) Mobile (compatible; Yahoo! Slurp; http://help.yahoo.com/help/us/ysearch/slurp)\n" +
          "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; NP06; rv:11.0) like Gecko\n" +
          "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.3; ARM; Trident/7.0; Touch; .NET4.0E; .NET4.0C; Tablet PC 2.0)\n" +
          "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.0.19) Gecko/2010062819 Firefox/3.0.19 Flock/2.6.1\n" +
          "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36\n" +
          "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; Touch; rv:11.0) like Gecko\n" +
          "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)\n" +
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10) AppleWebKit/600.1.25 (KHTML, like Gecko) Version/8.0 Safari/600.1.25\n" +
          "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36\n" +
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/600.1.25 (KHTML, like Gecko) Version/8.0 Safari/600.1.25\n" +
          "Mozilla/4.0 (compatible; MSIE 6.1; Windows XP; .NET CLR 1.1.4322; .NET CLR 2.0.50727)\n" +
          "Mozilla/5.0 (Linux; Android 4.4; Nexus 5 Build/_BuildID_) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36\n" +
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:39.0) Gecko/20100101 Firefox/39.0";
      let a = s.split("\n");
      let ls = [];
      for (let i = 0;i < a.length;i++) {
        ls[i] = {
          user_agent: a[i]
        }
      }
      this.access_list = ls;
    },
    utGetFlagEmoji,
    utTextDurationTimeHMS,
    utTextTimeYMD,
    utTextTimeHMAPM,
    utHexColorHashString,
    utParseUA,
    utSimpleVersion
  },
  mounted() {

    this.load_access_list();
  }
}
</script>







<style scoped>

.code {
  font-family: "Consolas", "Menlo",monospace;
}


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