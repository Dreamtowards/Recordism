package recordism.network.dao.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ip_infos")
public class IpInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    public int id;

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
