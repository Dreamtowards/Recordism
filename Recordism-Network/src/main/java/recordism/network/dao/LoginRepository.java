package recordism.network.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import recordism.network.dao.model.Login;

public interface LoginRepository extends JpaRepository<Login, Integer> {

    Login findByToken(String token);

}
