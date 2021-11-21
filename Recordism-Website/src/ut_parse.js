


function utGetFlagEmoji(countryCode) {
    if (!countryCode)
        return "🏳️";
    if (countryCode.toUpperCase() === "TW")
        return "🇼🇸";  // 🇼🇸 🇹🇴
    return String.fromCodePoint(...[...countryCode.toUpperCase()].map(x=>0x1f1a5+x.charCodeAt()));
}
function utTextDurationTimeHMS(sec) {
    let s = Math.floor(sec % 60);
    let m = Math.floor(sec / 60) % 60;
    let h = Math.floor(sec / 60 / 60);
    return uiText00(h)+":"+uiText00(m)+":"+uiText00(s);
}
function uiText00(n) {
    if (n < 10)
        return "0"+n;
    return ""+n;
}
function utTextTimeHMAPM(ms, sec) {
    let d = new Date(ms);
    let h = d.getHours();
    return uiText00(h > 12 ? h-12 : h)+":"+uiText00(d.getMinutes())+(sec ? ":"+uiText00(d.getSeconds()) : "")+" "+(h>12?"PM":"AM");
}
function utHashString(str) {
    let hash = 0;
    for (let i = 0; i < str.length; i++) {
        let char = str.charCodeAt(i);
        hash = ((hash << 5) - hash) + char;
        hash = hash & hash;
    }
    return hash;
}
function utTextTimeYMD(ms) {
    let d = new Date(ms);
    return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
}
function utHexColorHashString(str) {
    let s = Math.abs(utHashString(str)).toString(16);
    if (s.length < 6)
        s += "0".repeat(6-s.length);
    else
        s = s.substr(0, 6);
    return "#"+s;
}

function utResolutionName(w, h) {
    const RES_TABLE = [
        {name: "480p", std_name: "SD", w: 640,  h: 480},
        {name: "720p", std_name: "HD", w: 1280, h: 720},
        {name: "720p", std_name: "HD", w: 1280, h: 720},
        {name: "1080p", std_name: "FHD", w: 1920, h: 1080},
        {name: "1440p", std_name: "2k QHD", w: 2560, h: 1440},
        {name: "2160p", std_name: "4k UHD", w: 3840, h: 2160},
        {name: "4320p", std_name: "8k FUHD", w: 7680, h: 4320}];

    let resPx = w * h;
    let bestFitIdx = 0;
    let leastDif = Infinity;
    for (let i = 0;i < RES_TABLE.length;i++) {
        let e = RES_TABLE[i];
        let ePx = e.w * e.h;
        let dif = Math.abs(ePx - resPx);
        if (dif < leastDif) {
            bestFitIdx = i;
            leastDif = dif;
        }
    }
    return RES_TABLE[bestFitIdx];
}

function utSimpleVersion(strVer, nP) {
    let numP = 0;
    for (let i = 0;i < strVer.length;i++) {
        if (strVer.charAt(i) === '.')
            numP++;
        if (numP === nP)
            return strVer.substr(0, i);
    }
    return strVer;
}

// Win98 IE6          Mozilla/4.0 (compatible; MSIE 6.0; Windows 98)
// WinXP IE6.1        Mozilla/4.0 (compatible; MSIE 6.1; Windows XP; .NET CLR 1.1.4322; .NET CLR 2.0.50727)
// WinXP IE8          Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0)
// WinVista IE8       Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; WOW64; Trident/4.0;)
// WinVista IE9       Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.0)
// Win7 IE8           Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0)
// Win7 IE9           Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1)
// Win7 IE10          Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)
// Win7 IE11          Mozilla/5.0 (Windows NT 6.1; Trident/7.0; rv:11.0) like Gecko
// Win8 IE10          Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2)
// Win8 IE11          Mozilla/5.0 (Windows NT 6.2; Trident/7.0; rv:11.0) like Gecko
// Win8.1 IE11        Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko
// Win10 IE11         Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko
// Win10 EDGE:        Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.164 Safari/537.36 Edg/91.0.864.67
// Win10 Chrome       Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.164 Safari/537.36
// Win10 Chrome       Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.164 Safari/537.36
// Win10 Chrome       Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.164 Safari/537.36
// Win10 Safari:      Mozilla/5.0 (Windows; U; Windows NT 10.0; en-US) AppleWebKit/603.1.30 (KHTML, like Gecko) Version/11.0 Safari/603.1.30
// Win10 Firefox:     Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:90.0) Gecko/20100101 Firefox/90.0

// OSX Chrome 91.0.4472.114: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36
// OSX Safari 14.0.3: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0.3 Safari/605.1.15
// OSX BigSur         Mozilla/5.0 (Macintosh; Intel Mac OS X 11_4) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1.1 Safari/605.1.15
// iPhone             Mozilla/5.0 (iPhone; CPU iPhone OS 14_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1.1 Mobile/15E148 Safari/604.1
// iPad               Mozilla/5.0 (iPad; CPU OS 14_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1.1 Mobile/15E148 Safari/604.1
// iPod               Mozilla/5.0 (iPod touch; CPU iPhone 14_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1.1 Mobile/15E148 Safari/604.1

// Android Pixel2XL   Mozilla/5.0 (Linux; Android 8.0.0; Pixel 2 XL Build/OPD1.170816.004) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Mobile Safari/537.36
// Android RedMi      Mozilla/5.0 (Linux; Android 6.0.1; RedMi Note 5 Build/RB3N5C; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/68.0.3440.91 Mobile Safari/537.36
// Android Nexus      Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko; googleweblight) Chrome/38.0.1025.166 Mobile Safari/535.19
// Android Nexus      Mozilla/5.0 (Linux; Android 4.4.2; Nexus 4 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.114 Mobile Safari/537.36
// Android LG         Mozilla/5.0 (Linux; Android 6.0; LG-H631 Build/MRA58K) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/38.0.2125.102 Mobile Safari/537.36
// Android Samsung    Mozilla/5.0 (Linux; Android 9; SM-G960F Build/PPR1.180610.011; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/74.0.3729.157 Mobile Safari/537.36
// Android Samsung    Mozilla/5.0 (Linux; Android 9; SAMSUNG SM-G960U) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/10.2 Chrome/71.0.3578.99 Mobile Safari/537.36
// Android Samsung    Mozilla/5.0 (Linux; U; Android 4.3; de-de; GT-I9300 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30
// Android Mi A1      Mozilla/5.0 (Linux; Android 7.1; Mi A1 Build/N2G47H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.83 Mobile Safari/537.36

// WindowsPhone       Mozilla/5.0 (Mobile; Windows Phone 8.1; Android 4.0; ARM; Trident/7.0; Touch; rv:11.0; IEMobile/11.0; NOKIA; Lumia 635) like iPhone OS 7_0_3 Mac OS X AppleWebKit/537 (KHTML, like Gecko) Mobile Safari/537
// WindowsPhone       Mozilla/5.0 (Mobile; Windows Phone 8.1; Android 4.0; ARM; Trident/7.0; Touch; rv:11.0; IEMobile/11.0; Microsoft; Lumia 640 LTE) like iPhone OS 7_0_3 Mac OS X AppleWebKit/537 (KHTML, like Gecko) Mobile Safari/537
// BlackBerryOS       BlackBerry8520/5.0.0.681 Profile/MIDP-2.1 Configuration/CLDC-1.1 VendorID/600

// Linux X11          Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36


function utParseUA(ua) {
    function _getsuf(str, begin) {
        let i = str.indexOf(begin);
        if (i === -1)
            return "ERR";
        let suf = str.split(begin)[1];  // wasted.
        if (str.indexOf("(") < i && str.indexOf(")") > i) {
            return suf.split(/(;|\)|like)/)[0];
        } else {
            return suf.split(" ")[0];
        }
    }
    function _includesAll(str, searches) { for (let s of searches) if (!str.includes(s)) return false; return true; }
    let _deviceinfos = ua.includes("(") ? ua.split("(")[1].split(")")[0].split(";") : [];  // first parentheses
    let ual = ua.toLowerCase();

    let browser = {
        name: "Unknown",
        version: "NA"
    };
    if (ua.includes("Edg/"))   // Before Chrome.
    {
        browser.name = "Edge";
        browser.version = _getsuf(ua, "Edg/");
    }
    if (ua.includes("Edge/"))   // Before Chrome.  Old Edge.
    {
        browser.name = "Edge";
        browser.version = _getsuf(ua, "Edge/");
    }
    else if (ua.includes("Chromium/"))  // Before Chrome
    {
        browser.name = "Chromium";
        browser.version = _getsuf(ua, "Chromium/");
    }
    else if (ua.includes("Chrome/"))
    {
        browser.name = "Chrome";
        browser.version = _getsuf(ua, "Chrome/");
    }
    else if (ua.includes("CriOS/"))  // Legacy. Chrome on iOS.
    {
        browser.name = "Chromeios";
        browser.version = _getsuf(ua, "CriOS/");
    }
    else if (_includesAll(ual, ["version/", "applewebkit/", "safari/"]))
    {
        browser.name = "Safari";
        browser.version = _getsuf(ua, "Version/");
    }
    else if (ua.includes("Firefox/"))
    {
        browser.name = "Firefox";
        browser.version = _getsuf(ua, "Firefox/");
    }
    else if (_includesAll(ua, ["Trident/7.0", "rv:11.0"]))
    {
        browser.name = "IE";
        browser.version = "11";
    }
    else if (ua.includes("MSIE "))
    {
        browser.name = "IE";
        browser.version = _getsuf(ua, "MSIE ");
    }
    else if (ua.includes("Opera/"))
    {
        browser.name = "Opera";
        browser.version = _getsuf(ua, "Opera/");
    }
    else
    {
        if (ua.includes("Mercury/"))  // discontinued. iOS,Android. 2017down.  https://en.wikipedia.org/wiki/Mercury_Browser
        {
            browser.name = "Mercury Browser";
            browser.version = _getsuf(ua, "Mercury/");
        }
        else if (ua.includes("PaleMoon/"))
        {
            browser.name = "Pale Moon";
            browser.version = _getsuf(ua, "PaleMoon/");
        }
        else if (ua.includes("NintendoBrowser/"))
        {
            browser.name = "Nintendo Browser";
            browser.version = _getsuf(ua, "NintendoBrowser/");
        }
        else if (ual.includes("konqueror/"))
        {
            browser.name = "Konqueror Browser";
            browser.version = _getsuf(ual, "konqueror/");
        }
        else if (ua.includes("SeaMonkey/"))
        {
            browser.name = "SeaMonkey";
            browser.version = _getsuf(ua, "SeaMonkey/");
        }

        // UCBrowser
    }



    let OS = {
        name: "Unknown",
        version: "",
        arch: "",
        platform: ""  // mobile / desktop
    };
    if (ua.includes("Windows Phone"))  // Before Android.
    {
        OS.name = "Windows Phone";
        OS.version = _getsuf(ua, "Windows Phone ");
    }
    else if (_includesAll(ua, ["Windows", "NT"]) || ua.includes("Windows XP") || ua.includes("Windows 98"))
    {
        if (ua.includes("NT 10.0"))     OS.name = "Win10";
        else if (ua.includes("NT 6.3")) OS.name = "Win8.1";
        else if (ua.includes("NT 6.2")) OS.name = "Win8";
        else if (ua.includes("NT 6.1")) OS.name = "Win7";
        else if (ua.includes("NT 6.0")) OS.name = "WinVista";
        else if (ua.includes("NT 5.1") || ua.includes("NT 5.2") || ua.includes("Windows XP")) OS.name = "WinXP";
        else if (ua.includes("NT 5.0")) OS.name = "Win2000";
        else if (ua.includes("Windows 98")) OS.name = "Win98";

        OS.platform = "desktop";
        if (ua.includes("WOW64")) OS.arch += "wow64";
        if (ua.includes("Win64") || ua.includes("x64")) OS.arch += " x64";
        OS.arch = OS.arch.trim();
    }
    else if (ua.includes("Macintosh"))
    {
        OS.name = "MacOSX";
        OS.version = _getsuf(ua, "Mac OS X ").replaceAll("_", ".");
        OS.platform = "desktop";
    }
    else if (ua.includes("like Mac OS X"))
    {
        OS.name = "iOS";
        let vs = ua.split(" like Mac OS X")[0].split(" ");
        OS.version = vs[vs.length-1].replaceAll("_", ".");

        if (ua.includes("Mobile/"))
            OS.platform = "mobile";
        if (ua.includes("iPod touch")) OS.arch = "iPod";
        else if (ua.includes("iPad")) OS.arch = "iPad";
        else if (ua.includes("iPhone")) OS.arch = "iPhone";
    }
    else if (ua.includes("Android"))
    {
        OS.name = "Android";
        if (ua.includes("Mobile"))
            OS.platform = "mobile";
        for (let p of _deviceinfos) {
            if (p.includes("Android"))     OS.version = p.trim().split(" ")[1];
            else if (p.includes("Build/")) OS.arch = p.trim().split(" Build/")[0];  // DEVICE
        }
    }
    else if (ua.includes("Linux")) // After Android.
    {
        OS.name = "Linux";

        if (ua.includes("Ubuntu")) OS.arch += " Ubuntu"+_getsuf(ua, "Ubuntu");  // SUB-VERSION
        else if (ua.includes("GoogleTV")) OS.arch += " GoogleTV";  // DEVICE
    }
    else if (ua.includes("FreeBSD"))
    {
        OS.name = "FreeBSD";

        if (ua.includes("amd64")) OS.arch += " amd64";
        OS.arch = OS.arch.trim();
    }
    else
    {
        if (ua.includes("PLAYSTATION"))
        {
            OS.name = "PLAYSTATION";
            OS.version = _getsuf(ua, "PLAYSTATION ");
        }
        else if (ua.includes("CrOS"))
        {
            OS.name = "ChromeOS";
        }
        else if (ua.includes("BlackBerry"))
        {
            OS.name = "BlackBerry";
            OS.version = _getsuf(ua, "BlackBerry ");
        }
        else if (ua.includes("Nokia"))
        {
            OS.name = "Nokia";
            OS.version = _getsuf(ua, "Nokia");
        }
        else if (ua.includes("SunOS"))
        {
            OS.name = "SunOS";
        }
        else if (ua.includes("Nintendo "))
        {
            OS.name = "Nintendo";
            OS.version = _getsuf(ua, "Nintendo ");
        }
    }

    if (ua.includes("x86_64")) OS.arch += " x86_64";
    if (ua.includes("i686")) OS.arch += " i686";
    if (ua.includes("i386")) OS.arch += " i386";
    if (ual.includes("arm")) OS.arch += " arm"+_getsuf(ual, "arm");
    OS.arch = OS.arch.trim();

    let botkw = (ual.includes("bot") || ual.includes("spider") || ual.includes("crawler"));
    if (ua.includes("Yahoo! Slurp") || botkw || (botkw && ua.includes("http") && browser.name==="Unknown"))
    {
        browser.name = "Robot";
        browser.version = "NA";
        OS.name = "Robot";
        OS.version = "";
        OS.arch = "";
        OS.platform = "";
    }

    return {
        browser: browser,
        os: OS
    };
}

export {
    utGetFlagEmoji, utTextDurationTimeHMS, utTextTimeYMD, utTextTimeHMAPM,
    utHexColorHashString, utParseUA, utSimpleVersion, utResolutionName}