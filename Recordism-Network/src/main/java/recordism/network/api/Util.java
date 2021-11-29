package recordism.network.api;

public class Util {


    public static boolean isBlank(String s) {
        return s==null || s.isEmpty();
    }

    public static String urlToPath(String url) {
        int tail = url.indexOf('?');
        return tail==-1 ? url : url.substring(0, tail);
    }

    public static final Object[][] RES_SPCS = {
    {"480p",  "SD",  640,  480},
    {"720p",  "HD",  1280, 720},
    {"720p",  "HD",  1280, 720},
    {"1080p", "FHD", 1920, 1080},
    {"1440p", "2k QHD",  2560, 1440},
    {"2160p", "4k UHD",  3840, 2160},
    {"4320p", "8k FUHD", 7680, 4320}};

    public static String resolutionLevelName(int w, int h) {
        int rpx = w * h;
        int bestFitIdx = 0;
        int leastDif = Integer.MAX_VALUE;
        for (int i = 0;i < RES_SPCS.length;i++) {
            Object[] e = RES_SPCS[i];
            int ePx = (int)e[2] * (int)e[3];
            int dif = Math.abs(ePx - rpx);
            if (dif < leastDif) {
                bestFitIdx = i;
                leastDif = dif;
            }
        }
        return RES_SPCS[bestFitIdx][0] + " " + RES_SPCS[bestFitIdx][1];

    }



    private static String getsuf(String s, String from) {
        return s.substring(s.indexOf(from)+from.length());
    }
    private static boolean incall(String s, String... incs) {
        for (String e : incs) {
            if (!s.contains(e))
                return false;
        }
        return true;
    }

    public static String browserModel(String ua) {

//        let _deviceinfos = ua.contains("(") ? ua.split("(")[1].split(")")[0].split(";") : [];  // first parentheses
        String ual = ua.toLowerCase();

        String browserName = "Unknown";
        String browserVersion = "NA";
        
        if (ua.contains("Edg/")) {   // Before Chrome.{
            browserName = "Edge"; browserVersion = getsuf(ua, "Edg/");
        } if (ua.contains("Edge/")) {  // Before Chrome.  Old Edge.
            browserName = "Edge"; browserVersion = getsuf(ua, "Edge/");
        } else if (ua.contains("Chromium/")) {  // Before Chrome
            browserName = "Chromium"; browserVersion = getsuf(ua, "Chromium/");
        } else if (ua.contains("Chrome/")) {
            browserName = "Chrome"; browserVersion = getsuf(ua, "Chrome/");
        } else if (ua.contains("CriOS/")) {  // Legacy. Chrome on iOS.
            browserName = "Chromeios";
            browserVersion = getsuf(ua, "CriOS/");
        }
        else if (_includesAll(ual, ["version/", "applewebkit/", "safari/"]))
        {
            browserName = "Safari";
            browserVersion = getsuf(ua, "Version/");
        }
    else if (ua.contains("Firefox/"))
        {
            browserName = "Firefox";
            browserVersion = getsuf(ua, "Firefox/");
        }
        else if (_includesAll(ua, ["Trident/7.0", "rv:11.0"]))
        {
            browserName = "IE";
            browserVersion = "11";
        }
    else if (ua.contains("MSIE "))
        {
            browserName = "IE";
            browserVersion = getsuf(ua, "MSIE ");
        }
        else if (ua.contains("Opera/"))
        {
            browserName = "Opera";
            browserVersion = getsuf(ua, "Opera/");
        }
        else
        {
            if (ua.contains("Mercury/"))  // discontinued. iOS,Android. 2017down.  https://en.wikipedia.org/wiki/Mercury_Browser
            {
                browserName = "Mercury Browser";
                browserVersion = getsuf(ua, "Mercury/");
            }
            else if (ua.contains("PaleMoon/"))
            {
                browserName = "Pale Moon";
                browserVersion = getsuf(ua, "PaleMoon/");
            }
            else if (ua.contains("NintendoBrowser/"))
            {
                browserName = "Nintendo Browser";
                browserVersion = getsuf(ua, "NintendoBrowser/");
            }
            else if (ual.contains("konqueror/"))
            {
                browserName = "Konqueror Browser";
                browserVersion = getsuf(ual, "konqueror/");
            }
            else if (ua.contains("SeaMonkey/"))
            {
                browserName = "SeaMonkey";
                browserVersion = getsuf(ua, "SeaMonkey/");
            }

            // UCBrowser
        }



        let OS = {
                name: "Unknown",
                version: "",
                arch: "",
                platform: ""  // mobile / desktop
    };
        if (ua.contains("Windows Phone"))  // Before Android.
        {
            OS.name = "Windows Phone";
            OS.version = getsuf(ua, "Windows Phone ");
        }
        else if (_includesAll(ua, ["Windows", "NT"]) || ua.contains("Windows XP") || ua.contains("Windows 98"))
        {
            if (ua.contains("NT 10.0"))     OS.name = "Win10";
            else if (ua.contains("NT 6.3")) OS.name = "Win8.1";
            else if (ua.contains("NT 6.2")) OS.name = "Win8";
            else if (ua.contains("NT 6.1")) OS.name = "Win7";
            else if (ua.contains("NT 6.0")) OS.name = "WinVista";
            else if (ua.contains("NT 5.1") || ua.contains("NT 5.2") || ua.contains("Windows XP")) OS.name = "WinXP";
            else if (ua.contains("NT 5.0")) OS.name = "Win2000";
            else if (ua.contains("Windows 98")) OS.name = "Win98";

            OS.platform = "desktop";
            if (ua.contains("WOW64")) OS.arch += "wow64";
            if (ua.contains("Win64") || ua.contains("x64")) OS.arch += " x64";
            OS.arch = OS.arch.trim();
        }
    else if (ua.contains("Macintosh"))
        {
            OS.name = "MacOSX";
            OS.version = getsuf(ua, "Mac OS X ").replaceAll("_", ".");
            OS.platform = "desktop";
        }
        else if (ua.contains("like Mac OS X"))
        {
            OS.name = "iOS";
            let vs = ua.split(" like Mac OS X")[0].split(" ");
            OS.version = vs[vs.length-1].replaceAll("_", ".");

            if (ua.contains("Mobile/"))
                OS.platform = "mobile";
            if (ua.contains("iPod touch")) OS.arch = "iPod";
            else if (ua.contains("iPad")) OS.arch = "iPad";
            else if (ua.contains("iPhone")) OS.arch = "iPhone";
        }
        else if (ua.contains("Android"))
        {
            OS.name = "Android";
            if (ua.contains("Mobile"))
                OS.platform = "mobile";
            for (let p of _deviceinfos) {
            if (p.contains("Android"))     OS.version = p.trim().split(" ")[1];
            else if (p.contains("Build/")) OS.arch = p.trim().split(" Build/")[0];  // DEVICE
        }
        }
        else if (ua.contains("Linux")) // After Android.
        {
            OS.name = "Linux";

            if (ua.contains("Ubuntu")) OS.arch += " Ubuntu"+getsuf(ua, "Ubuntu");  // SUB-VERSION
            else if (ua.contains("GoogleTV")) OS.arch += " GoogleTV";  // DEVICE
        }
        else if (ua.contains("FreeBSD"))
        {
            OS.name = "FreeBSD";

            if (ua.contains("amd64")) OS.arch += " amd64";
            OS.arch = OS.arch.trim();
        }
        else
        {
            if (ua.contains("PLAYSTATION"))
            {
                OS.name = "PLAYSTATION";
                OS.version = getsuf(ua, "PLAYSTATION ");
            }
            else if (ua.contains("CrOS"))
            {
                OS.name = "ChromeOS";
            }
            else if (ua.contains("BlackBerry"))
            {
                OS.name = "BlackBerry";
                OS.version = getsuf(ua, "BlackBerry ");
            }
            else if (ua.contains("Nokia"))
            {
                OS.name = "Nokia";
                OS.version = getsuf(ua, "Nokia");
            }
            else if (ua.contains("SunOS"))
            {
                OS.name = "SunOS";
            }
            else if (ua.contains("Nintendo "))
            {
                OS.name = "Nintendo";
                OS.version = getsuf(ua, "Nintendo ");
            }
        }

        if (ua.contains("x86_64")) OS.arch += " x86_64";
        if (ua.contains("i686")) OS.arch += " i686";
        if (ua.contains("i386")) OS.arch += " i386";
        if (ual.contains("arm")) OS.arch += " arm"+getsuf(ual, "arm");
        OS.arch = OS.arch.trim();

        let botkw = (ual.contains("bot") || ual.contains("spider") || ual.contains("crawler"));
        if (ua.contains("Yahoo! Slurp") || botkw || (botkw && ua.contains("http") && browserName==="Unknown"))
        {
            browserName = "Robot";
            browserVersion = "NA";
            OS.name = "Robot";
            OS.version = "";
            OS.arch = "";
            OS.platform = "";
        }

        return {
                browser: browser,
                os: OS
    
    }

}
