package dev.mediaforum.network.api;

import dev.mediaforum.network.NetworkApplication;
import dev.mediaforum.network.conf.RestException;
import dev.mediaforum.network.dao.EventRepository;
import dev.mediaforum.network.dao.IpRepository;
import dev.mediaforum.network.dao.RecordRepository;
import dev.mediaforum.network.dao.model.AccessEvent;
import dev.mediaforum.network.dao.model.AccessRecord;
import dev.mediaforum.network.dao.model.IpInfo;
import dev.mediaforum.network.dao.model.User;
import dev.mediaforum.network.dao.UserRepository;
import dev.mediaforum.network.externalutil.CollectionUtils;
import dev.mediaforum.network.externalutil.HttpUtils;
import dev.mediaforum.network.externalutil.Validate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/", method = RequestMethod.POST)
public class RestMain {

    private static final Predicate<String> VALID_EMAIL_ADDRESS =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).asPredicate();

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public RecordRepository recordRepository;

    @Autowired
    public EventRepository eventRepository;

    @Autowired
    public IpRepository ipRepository;

    private static String utJoin(List<String> ls, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String l : ls) {
            if (sb.length() > 0)
                sb.append(delimiter);
            sb.append(l);
        }
        return sb.toString();
    }

    @RequestMapping("access_init")
    public Map<String, Object> access_init(@RequestBody Map<String, Object> data, HttpServletRequest request) throws IOException {
        int siteId = (int)data.get("site_id");
        String remoteIp = "183.217.174.226";//request.getRemoteAddr();

        AccessRecord r = new AccessRecord();

        r.accessId = UUID.randomUUID().toString();
        r.time = System.currentTimeMillis();
        r.timeLastKeepalive = r.time;

        Map<String, Object> dUser = (Map<String, Object>)data.get("user");
        r.siteId = siteId;
        r.browserFingerprint = (String)dUser.get("browser_fingerprint");
        r.browserCookieUid = (String)dUser.get("browser_cookie_uid");
        r.browserVendor = (String)dUser.get("browser_vendor");
        r.browserTimezone = (String)dUser.get("browser_timezone");
        r.browserLanguage = (String) dUser.get("browser_language");
        r.browserLanguages = utJoin((List<String>)dUser.get("browser_languages"), ",");
        r.devicePlatform = (String)dUser.get("device_platform");
        r.userAgent = (String)dUser.get("user_agent");
            Map<String, Object> dMs = (Map<String, Object>)dUser.get("metrics");
            r.screenWidth = (int)dMs.get("screen_width");
            r.screenHeight = (int)dMs.get("screen_height");
            r.screenBits = (int)dMs.get("screen_bits");
            r.browserInnerWidth = (int)dMs.get("browser_inner_width");
            r.browserInnerHeight = (int)dMs.get("browser_inner_height");
            r.browserOuterWidth = (int)dMs.get("browser_outer_width");
            r.browserOuterHeight = (int)dMs.get("browser_outer_height");

        Map<String, Object> dWindow = (Map<String, Object>)data.get("window");
        r.windowTitle = (String)dWindow.get("title");
        r.windowUrl = (String)dWindow.get("url");
        r.windowReferrerUrl = (String)dWindow.get("referrer_url");

        // Check and Save IP Info.
        IpInfo ipInfo = ipRepository.findByIp(remoteIp);
        if (ipInfo == null) {
            JSONObject rsIp = new JSONObject(HttpUtils.httpGet("http://ip-api.com/json/"+remoteIp));

            ipInfo = new IpInfo();
            ipInfo.ip = remoteIp;
            ipInfo.country = rsIp.getString("country");
            ipInfo.countryCode = rsIp.getString("countryCode");
            ipInfo.region = rsIp.getString("regionName");
            ipInfo.regionCode = rsIp.getString("region");
            ipInfo.city = rsIp.getString("city");

            ipInfo.lat = rsIp.getFloat("lat");
            ipInfo.lon = rsIp.getFloat("lon");
            ipInfo.timezone = rsIp.getString("timezone");
            ipInfo.isp = rsIp.getString("isp");

            ipRepository.saveAndFlush(ipInfo);
        }

        r.ip = ipInfo;
        recordRepository.saveAndFlush(r);

        return CollectionUtils.asMap(
                "access_id", r.accessId);
    }


    @RequestMapping("push_event")
    public void push_event(@RequestBody Map<String, Object> data) {

        AccessEvent e = new AccessEvent();
        e.serverTime = System.currentTimeMillis();

        e.accessId = (String)data.get("access_id");
        e.eventName = (String)data.get("event_name");
        e.eventData = new JSONObject((Map<String, Object>)data.get("event_data")).toString(0);
        e.browserTime = (long)data.get("browser_time");

        eventRepository.save(e);
    }


    @RequestMapping("keepalive")
    public void keepalive(@RequestBody Map<String, Object> data) {
        String accessId = (String)data.get("access_id");
        long browserTime = (long)data.get("browser_time");

        AccessRecord ar = recordRepository.findByAccessId(accessId);

        ar.timeLastKeepalive = System.currentTimeMillis();

        recordRepository.save(ar);
    }


    @RequestMapping("access_list")
    public List<AccessRecord> access_list(@RequestBody Map<String, Object> data) {
        int siteId = (int)data.get("site_id");

        List<AccessRecord> ls = recordRepository.findAllBySiteId(siteId);

        return ls;
    }



    public static boolean isOnline(AccessRecord r) {
        return System.currentTimeMillis() - r.timeLastKeepalive  < 18;
    }
}
