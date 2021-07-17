package dev.mediaforum.network.dao;

import dev.mediaforum.network.dao.model.AccessRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<AccessRecord, Integer> {

    AccessRecord findByAccessId(String accessId);

}
