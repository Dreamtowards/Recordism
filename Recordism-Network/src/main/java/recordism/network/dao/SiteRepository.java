package recordism.network.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value =
            "SELECT FLOOR(time/?1)*?1 AS beginTime, COUNT(access.id) AS visits, COUNT(DISTINCT access.ip_id) AS visitors\n" +
            "FROM access\n" +
            "GROUP BY beginTime", nativeQuery = true)
    Collection<TimeVisitsVisitors> queryAudienceVisitations(long intervalMS);

    interface TimeVisitsVisitors {
        long getBeginTime();
        int getVisits();
        int getVisitors();
    }

    @Query(value =
            "SELECT FLOOR(time/?1)*?1 AS beginTime, 1 as one, city AS field, COUNT(access.id) as visits, COUNT(DISTINCT access.ip_id) as visitors\n" +
            "FROM access, ip\n" +
            "WHERE access.ip_id = ip.id\n" +
            "GROUP BY beginTime, field", nativeQuery = true)
    Collection<PreferencesAnalysis> queryPreferencesAnalysis(long intervalMS);

    interface PreferencesAnalysis {
        long getBeginTime();
        Object getField();
        int getVisits();
        int getVisitors();
    }

}
