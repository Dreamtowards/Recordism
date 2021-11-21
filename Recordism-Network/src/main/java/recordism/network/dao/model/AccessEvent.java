package recordism.network.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRawValue;

import javax.persistence.*;

@Entity
@Table(name = "access_events")
public class AccessEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @ManyToOne
    @JoinColumn(name = "access_record")
    @JsonIgnore
    public AccessRecord accessRecord;

    public String name;  // Event Type Name.
    public long time;  // Server Time.
    public long browserTime;

    @JsonRawValue
    public String eventData;

}
