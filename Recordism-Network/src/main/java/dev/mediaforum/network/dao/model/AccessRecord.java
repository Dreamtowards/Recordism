package dev.mediaforum.network.dao.model;


import javax.persistence.*;

@Entity
@Table(name = "access_records")
public class AccessRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    public String accessId;  // every accessId identify an AccessRecord.

    public int siteId;

    public String ip;

    public long time;
    public long timeLastKeepalive;  // 0, or available.

    public String browserFingerprint;
    public String browserCookieUid;
    public String browserVendor;
    public String browserTimezone;
    public String browserLanguage;
    public String browserLanguages;  // split by ","

    public String devicePlatform;
    public String userAgent;

    public int screenWidth;
    public int screenHeight;
    public int screenBits;
    public int browserInnerWidth;
    public int browserInnerHeight;
    public int browserOuterWidth;
    public int browserOuterHeight;

    public String windowTitle;
    public String windowUrl;
    public String windowReferrerUrl;

}
