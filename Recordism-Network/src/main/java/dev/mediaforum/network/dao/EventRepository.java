package dev.mediaforum.network.dao;

import dev.mediaforum.network.dao.model.AccessEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<AccessEvent, Integer> {
}
