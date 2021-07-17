package dev.mediaforum.network.dao;

import dev.mediaforum.network.dao.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<Site, Integer> {
}
