package fact.it.cyclingrouteservice.controller;

import fact.it.cyclingrouteservice.model.CyclingRoute;
import fact.it.cyclingrouteservice.repository.CyclingRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class CyclingRouteController {

    @Autowired
    private CyclingRouteRepository cyclingRouteRepository;

    @PostConstruct
    public void fillDB(){
        if(cyclingRouteRepository.count()==0){
            cyclingRouteRepository.save(new CyclingRoute("drieprovincieroute",
                    "https://www.drieprovincienroute.be/sites/default/files/Welkom-6.jpg",
                    "123", "3945"));
            cyclingRouteRepository.save(new CyclingRoute("Fietsen door de bomen",
                    "https://www.visitlimburg.be/sites/default/files/public/styles/header_small/public/2020-02/fietslus-fietsen-door-de-bomen.jpg?h=457ab839&itok=bVsfQQQ9",
                    "456", "3980"));
            System.out.println(cyclingRouteRepository.findCyclingRouteByRouteCode("123").getName());

        }
    }

    @GetMapping("/cyclingRoutes")
    public List<CyclingRoute> all(){
        return cyclingRouteRepository.findAll();
    }

    @GetMapping("/cyclingRoutes/name/{name}")
    public List<CyclingRoute> getCyclingRoutesByName(@PathVariable String name){
        return cyclingRouteRepository.findCyclingRoutesByNameContaining(name);
    }

    @GetMapping("/cyclingRoutes/code/{routeCode}")
    public CyclingRoute getCyclingRouteByRouteCode(@PathVariable String routeCode){
        return cyclingRouteRepository.findCyclingRouteByRouteCode(routeCode);
    }

    @GetMapping("/cyclingRoutes/postcode/{postcode}")
    public List<CyclingRoute> getRoutesByPostcode(@PathVariable String postcode){
        return cyclingRouteRepository.findAllByPostcode(postcode);
    }

    @PostMapping("/cyclingRoutes/add")
    public CyclingRoute addRoute(@RequestBody CyclingRoute cyclingRoute){
        cyclingRouteRepository.save(cyclingRoute);
        return cyclingRoute;
    }

    @PutMapping("/cyclingRoutes/update")
    public CyclingRoute updateRoute(@RequestBody CyclingRoute updateCyclingRoute){
        CyclingRoute cyclingRoute = cyclingRouteRepository.findCyclingRouteByRouteCode(updateCyclingRoute.getRouteCode());

        cyclingRoute.setName(updateCyclingRoute.getName());
        cyclingRoute.setImg_url(updateCyclingRoute.getImg_url());
        cyclingRoute.setPostcode(updateCyclingRoute.getPostcode());

        cyclingRouteRepository.save(cyclingRoute);

        return cyclingRoute;
    }

    @DeleteMapping("/cyclingRoutes/delete/{routeCode}")
    public ResponseEntity deleteRoute(@PathVariable String routeCode){
        CyclingRoute cyclingRoute = cyclingRouteRepository.findCyclingRouteByRouteCode(routeCode);
        if(cyclingRoute!=null){
            cyclingRouteRepository.delete(cyclingRoute);
        }
        return ResponseEntity.ok().build();
    }
}