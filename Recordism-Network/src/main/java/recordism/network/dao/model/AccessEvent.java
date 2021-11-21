package recordism.network.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRawValue;

import javax.persistence.*;

@Entity
@Table(name = "event")
public class AccessEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @ManyToOne
    @JsonIgnore
    public AccessRecord access;

    public String name;  // Event Type Name.
    public long time;  // Server Time.
//    public long browserTime;

    @JsonRawValue
    @Lob
    public String eventData;

}
