package recordism.network.api;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import recordism.network.dao.*;
import recordism.network.dao.model.*;
import recordism.network.externalutil.CollectionUtils;
import recordism.network.externalutil.HttpUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

import static recordism.network.api.Util.isBlank;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/", method = RequestMethod.POST)
public class RestMain {

//    private static final Predicate<String> VALID_EMAIL_ADDRESS =
//            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).asPredicate();

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public RecordRepository recordRepository;

    @Autowired
    public EventRepository eventRepository;

    @Autowired
    public IpRepository ipRepository;

    private static String utJoin(JSONArray ls, String delimiter) {
        StringBuilder sb = new StringBuilder();
        ls.forEach(e -> {
            if (sb.length() > 0)
                sb.append(delimiter);
            sb.append(e);
        });
        return sb.toString();
    }


    /**
     * =========================================
     *             WEB TAG REQUESTS
     * =========================================
     */

    @RequestMapping("access_init")
    public Map<String, Object> access_init(@RequestBody Map<String, Object> _d, HttpServletRequest request) throws IOException {  JSONObject data = new JSONObject(_d);
        int siteId = data.getInt("site_id");
        String remoteIp = "118.212.191.246";//"183.217.174.226";//request.getRemoteAddr();

        AccessRecord r = new AccessRecord();
        r.accessId = UUID.randomUUID().toString();
        r.time = System.currentTimeMillis();
        r.timeLastKeepalive = r.time;

        JSONObject uinfo = data.getJSONObject("user");
        r.siteId = siteId;
        r.browserFingerprint = uinfo.getString("browser_fingerprint");
        r.browserCookieUid   = uinfo.getString("browser_cookie_uid");
        r.browserVendor      = uinfo.getString("browser_vendor");
        r.browserTimezone    = uinfo.getString("browser_timezone");
        r.browserLanguage    = uinfo.getString("browser_language");
        r.browserLanguages = utJoin(uinfo.getJSONArray("browser_languages"), ",");
        r.devicePlatform = uinfo.getString("device_platform");
        r.userAgent = uinfo.getString("user_agent");
            JSONObject dmetrics = uinfo.getJSONObject("metrics");
            r.screenWidth  = dmetrics.getInt("screen_width");
            r.screenHeight = dmetrics.getInt("screen_height");
            r.screenBits   = dmetrics.getInt("screen_bits");
            r.browserInnerWidth  = dmetrics.getInt("browser_inner_width");
            r.browserInnerHeight = dmetrics.getInt("browser_inner_height");
            r.browserOuterWidth  = dmetrics.getInt("browser_outer_width");
            r.browserOuterHeight = dmetrics.getInt("browser_outer_height");

        JSONObject dwindow = data.getJSONObject("window");
        r.windowTitle = dwindow.getString("title");
        r.windowUrl = dwindow.getString("url");
        r.windowReferrerUrl = dwindow.optString("referrer_url");

        // Cached makeup
        r.cachedWindowPath = Util.urlToPath(r.windowUrl);
        r.cachedWindowReferrerPath = Util.urlToPath(r.windowReferrerUrl);
        r.cachedScreenResolutionLevel = Util.resolutionLevelName(r.screenWidth, r.screenHeight);
        r.cachedBrowserModel = ;

        // Check and Save IP Info.
        IpInfo ipInfo = ipRepository.findByIp(remoteIp);
        if (ipInfo == null) {
            ipInfo = new IpInfo();
            ipInfo.ip = remoteIp;

            try {
                JSONObject rsIp = new JSONObject(HttpUtils.httpGet("http://ip-api.com/json/"+remoteIp));
                ipInfo.country = rsIp.getString("country");
                ipInfo.countryCode = rsIp.getString("countryCode");
                ipInfo.region = rsIp.getString("regionName");
                ipInfo.regionCode = rsIp.getString("region");
                ipInfo.city = rsIp.getString("city");

                ipInfo.lat = rsIp.getFloat("lat");
                ipInfo.lon = rsIp.getFloat("lon");
                ipInfo.timezone = rsIp.getString("timezone");
                ipInfo.isp = rsIp.getString("isp");

            } catch (Exception ex) {
                // Unable to resolve the ip info.
//                throw new RuntimeException("Failed to resolute IP info. "+rsIp+"; Ip:"+remoteIp, ex);
            }
            ipRepository.saveAndFlush(ipInfo);
        }

        r.ip = ipInfo;
        recordRepository.saveAndFlush(r);

        return CollectionUtils.asMap(
                "access_id", r.accessId);
    }


    @RequestMapping("event_push")
    public void push_event(@RequestBody Map<String, Object> _d) { JSONObject data = new JSONObject(_d);
        String accessId = data.getString("access_id");

        AccessEvent e = new AccessEvent();
        e.time = System.currentTimeMillis();
        e.access = recordRepository.findByAccessId(accessId);
        e.name = data.getString("event_type");
        e.eventData = data.getJSONObject("event_data").toString(0);
        // e.browserTime = (long)data.get("browser_time");

        eventRepository.save(e);
    }


    @RequestMapping("access_keepalive")
    public void keepalive(@RequestBody Map<String, Object> data) {
        String accessId = (String)data.get("access_id");

        AccessRecord ar = recordRepository.findByAccessId(accessId);

        ar.timeLastKeepalive = System.currentTimeMillis();

        recordRepository.save(ar);
    }


    /**
     * =========================================
     *                   ACCESS
     * =========================================
     */

    @RequestMapping("access_list")
    public List<AccessRecord> access_list(@RequestBody Map<String, Object> _d) {  JSONObject data = new JSONObject(_d);
        int siteId = data.getInt("site_id");
        JSONObject sparams   = data.getJSONObject("search_params");
        String spIpAddr      = sparams.getString("ip_address");
        String spUaContains  = sparams.getString("useragent");
        String spUrlContains = sparams.getString("url");
        String spBrowserTimezone = sparams.getString("browser_timezone");
        String spBrowserLanguage = sparams.getString("browser_language");

        int pageIdx = 0;
        int pageSize = 20;

        Pageable pageable = PageRequest.of(pageIdx, pageSize, Sort.Direction.DESC, "time");

        Page<AccessRecord> page = recordRepository.findAll((Specification<AccessRecord>)(r, qry, cb) -> {
            List<Predicate> l = new ArrayList<>();
            l.add(cb.equal(r.get("siteId").as(Integer.class), siteId));

            if (!isBlank(spIpAddr)) {
                Join<AccessRecord, IpInfo> rIp = r.join("ip");
                l.add(cb.like(rIp.get("ip").as(String.class), "%"+spIpAddr+"%"));
            }
            if (!isBlank(spUaContains))
                l.add(cb.like(r.get("userAgent").as(String.class), "%"+spUaContains+"%"));
            if (!isBlank(spUrlContains))
                l.add(cb.like(r.get("windowUrl").as(String.class), "%"+spUrlContains+"%"));
            if (!isBlank(spBrowserTimezone))
                l.add(cb.like(r.get("browserTimezone").as(String.class), "%"+spBrowserTimezone+"%"));
            if (!isBlank(spBrowserLanguage))
                l.add(cb.like(r.get("browserLanguages").as(String.class), "%"+spBrowserLanguage+"%"));
            return cb.and(l.toArray(new Predicate[0]));
        }, pageable);

        return page.getContent();
    }


    /**
     * =========================================
     *                   USER
     * =========================================
     */

    @RequestMapping("user_register")
    public Map<String, Object> userRegister(@RequestBody Map<String, Object> _d) { JSONObject data = new JSONObject(_d);

        String username = data.getString("username");
        String passwordDigest = data.getString("password_digest");

        if (userRepository.findByUsername(username) != null)
            throw new RuntimeException("This Email already existed.");

        User usr = new User();
        usr.username = username;
        usr.passwordDigest = passwordDigest;
        usr.registerTime = System.currentTimeMillis();
        userRepository.saveAndFlush(usr);

        return CollectionUtils.asMap(
                "user_id", usr.id
        );
    }

    @Autowired
    private LoginRepository loginRepository;

    @RequestMapping("user_login")
    public Map<String, Object> userLogin(@RequestBody Map<String, Object> _d) { JSONObject data = new JSONObject(_d);

        String account = data.getString("account");
        String passwordDigest = data.getString("password_digest");

        User user;
        if ((user=userRepository.findByUsername(account)) == null)
            throw new RuntimeException("Account not found.");

        if (!user.passwordDigest.equals(passwordDigest))
            throw new RuntimeException("Incorrect password");

        String token = UUID.randomUUID().toString();
        Login login = new Login();
        login.user = user;
        login.token = token;
        login.time = System.currentTimeMillis();
        loginRepository.saveAndFlush(login);

        return CollectionUtils.asMap(
                "token", token
        );
    }

    private User checkUser(Map<String, Object> d) {
        String token = (String)d.get("token");

        Login l = loginRepository.findByToken(token);
        if (l == null)
            throw new RuntimeException("Invalid session.");

        return l.user;
    }


    @RequestMapping("user_profile")
    public User userProfile(@RequestBody Map<String, Object> _d) { JSONObject data = new JSONObject(_d);
        return checkUser(_d);
    }

    /**
     * =========================================
     *                   SITE
     * =========================================
     */

    @Autowired
    private SiteRepository siteRepository;

    @RequestMapping("site_new")
    public Map<String, Object> siteNew(@RequestBody Map<String, Object> _d) { JSONObject data = new JSONObject(_d);
        User user = checkUser(_d);

        String siteAddress = data.getString("site_address");

        if (siteRepository.findByAddress(siteAddress) != null)
            throw new RuntimeException("This site address already existed.");

        Site site = new Site();
        site.user = user;
        site.address = siteAddress;
        siteRepository.saveAndFlush(site);

        return CollectionUtils.asMap(
                "site_id", site.id
        );
    }

    // TEMP
    @RequestMapping("site_overview")
    public Map<String, Object> site_metrics(@RequestBody Map<String, Object> data) {

        long msCurr = System.currentTimeMillis();
        long numOnlinePages = recordRepository.count((Specification<AccessRecord>) (r, query, cb) ->
                cb.le(cb.diff(msCurr, r.get("timeLastKeepalive").as(long.class)), 10_000));  // KEEPALIVE_MAX

        return CollectionUtils.asMap(
                "online_users", 1,
                "online_pages", numOnlinePages
        );
    }




    @RequestMapping("site_heatmap")
    public Map<String, Object> siteHeatmap(@RequestBody Map<String, Object> _d) { JSONObject data = new JSONObject(_d);

        return CollectionUtils.asMap(
                "heatmap", siteRepository.queryHeatmap()
        );
    }


    @RequestMapping("site_audience_visitations")
    public Map<String, Object> siteVisitsNum(@RequestBody Map<String, Object> _d) { JSONObject data = new JSONObject(_d);

        long interval = data.getLong("interval");

        return CollectionUtils.asMap(
                "sample", siteRepository.queryAudienceVisitations(interval)
        );
    }

    @Autowired
    private EntityManager entityManager;

    @RequestMapping("site_preferences_analysis")
    public Map<String, Object> siteVisitsPref(@RequestBody Map<String, Object> _d) { JSONObject data = new JSONObject(_d);

        long interval = data.getLong("interval");
        int intervalSlicesLimit = data.getInt("interval_slices_limit");

        String counting = data.getString("counting");

        String category = data.getString("category");
        int categoryItemsLimit = data.getInt("category_items_limit");

//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//
//        CriteriaQuery<?> cq = cb.createQuery();
//        Root<?> r = cq.from(AccessRecord.class);
//
//        cq.multiselect();
//
//        entityManager.createQuery(cq).getResultList();


        String qlCountingDest;
        if (counting.equals("visits")) qlCountingDest = "a.id";
        else if (counting.equals("visitors")) qlCountingDest = "DISTINCT a.ip";
        else throw new IllegalStateException("Illegal enum for 'counting'");


        String qlItemField;
        switch (category) {
        case "country": qlItemField = "i.country"; break;
        case "region": qlItemField = "i.region"; break;
        case "city": qlItemField = "i.city"; break;
        case "isp": qlItemField = "i.isp"; break;
        case "ip_timezone": qlItemField = "i.timezone"; break;
        case "browser_timezone": qlItemField = "a.browserTimezone"; break;
        case "browser_language": qlItemField = "a.browserLanguage"; break;
        case "browser_vendor": qlItemField = "a.browserVendor"; break;
        case "device_platform": qlItemField = "a.devicePlatform"; break;
        case "window_title": qlItemField = "a.windowTitle"; break;
        case "window_url": qlItemField = "a.windowUrl"; break;
        case "window_referrer_url": qlItemField = "a.windowReferrerUrl"; break;
        default: throw new IllegalStateException("Illegal enum for 'category'");
        }
        // Operation System
        // Browser


        List<Object[]> rs = entityManager.createQuery(
                "SELECT FLOOR(a.time/"+interval+"L)*"+interval+"L AS time, "+qlItemField+" AS item, COUNT("+qlCountingDest+") AS count " +
                        "FROM AccessRecord a, IpInfo i " +
                        "WHERE a.ip = i " +
                        "GROUP BY time, item").getResultList();

        /** samples: {
         *     "2301983019230": [
         *      {item: 'Good', count 1}
         *     ]
         * }  */
        JSONObject samps = new JSONObject();
        for (Object[] row : rs) {
            long time = (long)row[0]; String sTime = String.valueOf(time);
            String item = (String)row[1];
            long count = (long)row[2];
            JSONObject el = new JSONObject(CollectionUtils.asMap(
                    "item", item,
                    "count", count));

            if (samps.has(sTime)) {
                samps.getJSONArray(sTime).put(el);
            } else {
                samps.put(sTime, new JSONArray().put(el));
            }
        }

        return CollectionUtils.asMap(
                "sample", samps.toMap()
        );
    }

}
