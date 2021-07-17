package dev.mediaforum.network.dao.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ip_infos")
public class IpInfo {

    @Id
    public String ip;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "ip")
//    public List<AccessRecord> recordList;

    public String country;
    public String countryCode;
    public String region;
    public String regionCode;
    public String city;

    public float lat;
    public float lon;

    public String timezone;
    public String isp;

}
