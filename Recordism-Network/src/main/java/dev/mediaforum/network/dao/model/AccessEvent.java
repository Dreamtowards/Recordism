package dev.mediaforum.network.dao.model;

import javax.persistence.*;

@Entity
@Table(name = "access_events")
public class AccessEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    public String accessId;

    public String eventName;
    public long serverTime;
    public long browserTime;

    public String eventData;

}
