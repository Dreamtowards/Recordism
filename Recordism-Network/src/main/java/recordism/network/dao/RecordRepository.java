package recordism.network.dao;

import recordism.network.dao.model.AccessRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RecordRepository extends JpaRepository<AccessRecord, Integer>, JpaSpecificationExecutor<AccessRecord> {

    AccessRecord findByAccessId(String accessId);

    List<AccessRecord> findAllBySiteId(int siteId);

}
