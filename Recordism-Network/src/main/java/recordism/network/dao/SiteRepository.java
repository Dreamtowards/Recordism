package recordism.network.dao;

import org.springframework.data.jpa.repository.Query;
import recordism.network.dao.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface SiteRepository extends JpaRepository<Site, Integer> {

    Site findByAddress(String address);


    @Query(value =
            "SELECT country, city, lat, lon, MAX(access.time) AS lastVisitTime, COUNT(access.id) AS visits, COUNT(DISTINCT access.ip_id) AS visitors\n" +
            "FROM access, ip\n" +
            "WHERE access.ip_id = ip.id \n" +
            "GROUP BY country, city, lat, lon", nativeQuery = true)
    Collection<HeatmapElement> queryHeatmap();

    interface HeatmapElement {
        String getCountry();
        String getCity();
        float getLat();
        float getLon();
        long getLastVisitTime();
        int getVisits();
        int getVisitors();
    }

}
