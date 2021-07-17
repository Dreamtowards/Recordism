
# The Recordary™ Protocols Specification

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


# API Interfaces


### [CP] `api/access_init` 

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
    ~~metrics~~: {
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

### [CP] `api/push_event`

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


### [CP] `api/keepalive`
TODO: make it belong to one of event. push_event  
payload:
```json5
{
  access_id: "",
  browser_time: "",  // timestamp_millis
}
```








### [CP] `api/access_list`

payload:
```json5
{
  site_id: 1,
}
```

resp:
```json5
{
  access_list: [
    {
      access_id: "",
      time: "",
      time_last_keepalive: "",
      ip: "",
      ip_info: {
        country: "",
        countryCode: "",
        city: "",
      },
      fingerprint: "",
      browser_cookie_uid: "",
      
    }
  ]
}
```






