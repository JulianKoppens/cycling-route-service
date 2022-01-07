package fact.it.cyclingrouteservice.repository;

import fact.it.cyclingrouteservice.model.CyclingRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CyclingRouteRepository extends JpaRepository<CyclingRoute, Integer> {
    CyclingRoute findCyclingRouteByRouteCode(String RouteCode);
    List<CyclingRoute> findCyclingRoutesByNameContaining(String name);
    List<CyclingRoute> findAllByPostcode(String postcode);
}
