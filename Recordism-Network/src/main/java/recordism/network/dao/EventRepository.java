package recordism.network.dao;

import recordism.network.dao.model.AccessEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<AccessEvent, Integer> {
}
