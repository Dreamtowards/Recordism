package dev.mediaforum.network.dao.model;


import javax.persistence.*;

@Entity
@Table(name = "ip_infos")
public class IpInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    public String ip;

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
