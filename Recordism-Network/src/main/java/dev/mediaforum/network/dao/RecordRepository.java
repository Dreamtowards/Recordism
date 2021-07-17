package dev.mediaforum.network.dao;

import dev.mediaforum.network.dao.model.AccessRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<AccessRecord, Integer> {

    AccessRecord findByAccessId(String accessId);

    List<AccessRecord> findAllBySiteId(int siteId);

}
