package fact.it.cyclingrouteservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.cyclingrouteservice.model.CyclingRoute;
import fact.it.cyclingrouteservice.repository.CyclingRouteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CyclingRouteControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CyclingRouteRepository cyclingRouteRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void GetAllRoutes() throws Exception {
        CyclingRoute cyclingRoute1 = new CyclingRoute("drieprovincieroute",
                "url1",
                "123", "3945");
        CyclingRoute cyclingRoute2 = new CyclingRoute("Fietsen door de bomen",
                "url2",
                "456", "3980");

        List<CyclingRoute> cyclingRouteList = new ArrayList<>();
        cyclingRouteList.add(cyclingRoute1);
        cyclingRouteList.add(cyclingRoute2);

        given(cyclingRouteRepository.findAll()).willReturn(cyclingRouteList);

        mockMvc.perform(get("/cyclingRoutes"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("drieprovincieroute")))
                .andExpect(jsonPath("$[0].img_url", is("url1")))
                .andExpect(jsonPath("$[0].routeCode", is("123")))
                .andExpect(jsonPath("$[0].postcode", is("3945")))

                .andExpect(jsonPath("$[1].name", is("Fietsen door de bomen")))
                .andExpect(jsonPath("$[1].img_url", is("url2")))
                .andExpect(jsonPath("$[1].routeCode", is("456")))
                .andExpect(jsonPath("$[1].postcode", is("3980")));

    }


    @Test
    public void GetRoutesByName() throws Exception {
        CyclingRoute cyclingRoute1 = new CyclingRoute("drieprovincieroute",
                "url1",
                "123", "3945");

        List<CyclingRoute> cyclingRouteList = new ArrayList<>();
        cyclingRouteList.add(cyclingRoute1);

        given(cyclingRouteRepository.findCyclingRoutesByNameContaining("drieprovincieroute")).willReturn(cyclingRouteList);

        mockMvc.perform(get("/cyclingRoutes/name/{name}", "drieprovincieroute"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("drieprovincieroute")))
                .andExpect(jsonPath("$[0].img_url", is("url1")))
                .andExpect(jsonPath("$[0].routeCode", is("123")))
                .andExpect(jsonPath("$[0].postcode", is("3945")));
    }

    @Test
    public void GetRoutesByCode() throws Exception{
        CyclingRoute cyclingRoute1 = new CyclingRoute("drieprovincieroute",
                "url1",
                "123", "3945");

        given(cyclingRouteRepository.findCyclingRouteByRouteCode("123")).willReturn(cyclingRoute1);

        mockMvc.perform(get("/cyclingRoutes/code/{routeCode}", "123"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("drieprovincieroute")))
                .andExpect(jsonPath("$.img_url", is("url1")))
                .andExpect(jsonPath("$.routeCode", is("123")))
                .andExpect(jsonPath("$.postcode", is("3945")));


    }
    @Test
    public void GetroutesByPostcode() throws Exception {
        CyclingRoute cyclingRoute1 = new CyclingRoute("drieprovincieroute",
                "url1",
                "123", "3945");

        List<CyclingRoute> cyclingRouteList = new ArrayList<>();
        cyclingRouteList.add(cyclingRoute1);

        given(cyclingRouteRepository.findAllByPostcode("3945")).willReturn(cyclingRouteList);

        mockMvc.perform(get("/cyclingRoutes/postcode/{postcode}", "3945"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("drieprovincieroute")))
                .andExpect(jsonPath("$[0].img_url", is("url1")))
                .andExpect(jsonPath("$[0].routeCode", is("123")))
                .andExpect(jsonPath("$[0].postcode", is("3945")));

    }



    @Test
    public void PostRoute() throws Exception{
        CyclingRoute cyclingRoute2 = new CyclingRoute("vierprovincieroute",
                "url7",
                "147", "3983");

        mockMvc.perform(post("/cyclingRoutes/add")
                .content(mapper.writeValueAsString(cyclingRoute2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("vierprovincieroute")))
                .andExpect(jsonPath("$.img_url", is("url7")))
                .andExpect(jsonPath("$.routeCode", is("147")))
                .andExpect(jsonPath("$.postcode", is("3983")));
    }


}