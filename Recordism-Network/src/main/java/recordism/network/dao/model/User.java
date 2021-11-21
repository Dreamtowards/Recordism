package recordism.network.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    public String email;

    public String username;

    public String passwordDigest;  // lowCase(toHexStr(sha256(pwd)));

    public long registerTime;

    @OneToMany
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("user")
    private List<Site> sites;

    public List<Site> getSites() {
        return sites;
    }
}
