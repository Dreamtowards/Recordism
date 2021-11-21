package recordism.network.dao;

import recordism.network.dao.model.IpInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpRepository extends JpaRepository<IpInfo, Integer> {

    IpInfo findByIp(String ip);

}
