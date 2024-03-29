package recordism.network.dao.model;

import javax.persistence.*;

@Entity
@Table(name = "site")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @ManyToOne
    public User user;

    public String address;

}
