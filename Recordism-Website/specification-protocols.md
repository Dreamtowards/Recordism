
# The Recordism™ Protocols Specification

# File Format

### Single Access Record

Everytime webpage load. include all the information of a user webload, 
the events.
export from console.


```json5
{
    access_id: "23cc2e16-8886-4f06-ae9e-32f1d06d6a78",
    user: {
        ip: "1.42.51.148", 
        time: "",
        time_last_keepalive: "",
        browser_fingerprint: "b59581e3dea9ad5e72933cbd35b696c8",
        browser_cookie_uid: "345eb47aa693e21bf291",
        browser_vendor: "Google Inc.",
        browser_timezone: "Asia/Shanghai",
        browser_language: "en",
        browser_languages: ["en", "zh-CN", "zh"],
        device_platform: "MacIntel",
        user_agent: "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36",
        metrics: {
            screen_width: 2160, 
            screen_height: 1440,
            screen_bits: "24",
            browser_inner_width: 1380,
            browser_inner_height: 800,
            browser_outer_width: 1380, 
            browser_outer_height: 900
        },
        ip_info: {
            country: "",
            countryCode: "",
            region: "",
            regionCode: "",
            city: "",
            lat: "",
            lon: "",
            timezone: "",
            isp: "",
        }
    },
    window: {
        title: "",
        url: "",
        referrer_url: ""
    },
    events: [
        {
            name: "url_changed",   // event_name
            time: "",              // server_time
            browser_time: "",      // browser_time
            old_url: "",
            new_url: "",
        }, 
        {
            name: "link_click",
            time: "",
            browser_time: "",
            current_url: "",
            target_url: "",
        }, 
        {
            name: "load_complete",
            time: "",
            browser_time: "",
            used_time: "",
        },
        {
            name: "visibility_changed",
            time: "",
            browser_time: "",
            to: "visible/hidden",
        }
    ]
}
```

### Supportation on Traditional Webpage and Modern Reactive WebClient

- Traditional Webpages.  
  Originally Fit. Pretty records on Everytime access/load pages.
  
- Reactive WebClient.
  Also pretty fit. Keepalive duration will be record. every event will be record.  
  Not missing much. just statistic way is different, 
  the PageLoad-Number been meaningless, instead, 
  the alive-duration and event-link-click(in-site-jump) is meanness.


### Application and Analytic on Properties.

- `time_last_keepalive`  
  The timestamp at last-keepalive on that access. use this to minus `time`, will get 'stay-time on that access'.
  
- `browser_fingerprint`  
  Not very ok. the changing-factor is a little bit much. IP and Cookie-UID often more reliable.

- `browser_cookie_uid`
  

# API Interfaces


#### [C] `api/access_init` 

payload:
```json5
{
  site_id: 1,
  user: {
    browser_fingerprint: "b59581e3dea9ad5e72933cbd35b696c8",
    browser_cookie_uid: "345eb47aa693e21bf291",
    browser_vendor: "Google Inc.",
    browser_timezone: "Asia/Shanghai",
    browser_language: "en",
    browser_languages: ["en", "zh-CN", "zh"],
    device_platform: "MacIntel",
    user_agent: "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36",
    metrics: {
      screen_width: 2160,
      screen_height: 1440,
      screen_bits: "24",
      browser_inner_width: 1380,
      browser_inner_height: 800,
      browser_outer_width: 1380,
      browser_outer_height: 900
    }
  },
  window: {
    title: "",
    url: "",
    referrer_url: ""
  }
}
```
resp:
```json5
{
  access_id: ""
}
```

#### [CP] `api/push_event`

payload:
```json5
{
  access_id: "",
  event_name: "url_changed",  // event_name
  event_data: {
    old_url: "",
    new_url: "",
  },
  browser_time: "",  // timestamp_millis
}
```


#### [CP] `api/keepalive`
TODO: make it belong to one of event. push_event  
payload:
```json5
{
  access_id: ""
}
```








### [CP] `api/access_list`

payload:
```json5
{
  site_id: 1,
  search_params: {  // optical members.
    ip_address: "",
    useragent: "",
    url: "",
    browser_timezone: "",
    browser_language: ""
  }
}
```

resp:
```json5
{
  access_list: [
    {
      "access_id": "234f930a-4a22-490c-bb02-9c51a33c076f",
      "time": 1626575861238,
      "time_last_keepalive": 1626576149547,
      "browser_fingerprint": "b59581e3dea9ad5e72933cbd35b696c8",
      "browser_cookie_uid": "ac789d418e3454e1b54f",
      "browser_vendor": "Google Inc.",
      "browser_timezone": "Asia/Shanghai",
      "browser_language": "en",
      "browser_languages": "en,zh-CN,zh",
      "device_platform": "MacIntel",
      "user_agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36",
      "screen_width": 2560,
      "screen_height": 1440,
      "screen_bits": 24,
      "browser_inner_width": 1553,
      "browser_inner_height": 695,
      "browser_outer_width": 1553,
      "browser_outer_height": 806,
      "window_title": "Test Title",
      "window_url": "http://...",
      "window_referrer_url": null,
      "ip_info": {
        "ip": "183.217.174.226",
        "country": "China",
        "country_code": "CN",
        "region": "Guangdong",
        "region_code": "GD",
        "city": "Guangzhou",
        "lat": 23.1181,
        "lon": 113.254,
        "timezone": "Asia/Shanghai",
        "isp": "China Mobile"
      },
      "access_events": [
        {
          "id": 1,
          "name": null,
          "time": 0,
          "browser_time": 1626769382755,
          "event_data": {
            "used_time": 5111
          }
        },
        {
          "id": 2,
          "name": null,
          "time": 0,
          "browser_time": 1626769410979,
          "event_data": {
            "current_url": "http://localhost:63342/Recordism-Webtag/Accessrecord/src/test_page.html?_ijt=6bk42loumakt99eugjq6rlli8n",
            "target_url": "http://localhost:63342/Recordism-Webtag/Accessrecord/src/test_page.html?_ijt=6bk42loumakt99eugjq6rlli8n#abc"
          }
        },
        {
          "id": 3,
          "name": null,
          "time": 0,
          "browser_time": 1626769411041,
          "event_data": {
            "new_url": "http://localhost:63342/Recordism-Webtag/Accessrecord/src/test_page.html?_ijt=6bk42loumakt99eugjq6rlli8n#abc",
            "old_url": "http://localhost:63342/Recordism-Webtag/Accessrecord/src/test_page.html?_ijt=6bk42loumakt99eugjq6rlli8n"
          }
        }
      ]
    }
  ]
}
```




### [CP] `api/site_metrics`

payload:
```json5
{
  site_id: 1
}
```

resp:
```json5
{
  "online_users": 1,
  "online_pages": 1,
//  "today_pageviews": 1,
//  "today_users": 1
}
```
