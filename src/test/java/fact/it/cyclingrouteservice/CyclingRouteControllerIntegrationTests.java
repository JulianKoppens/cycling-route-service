package fact.it.cyclingrouteservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.cyclingrouteservice.model.CyclingRoute;
import fact.it.cyclingrouteservice.repository.CyclingRouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CyclingRouteControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CyclingRouteRepository cyclingRouteRepository;

    private CyclingRoute cyclingRoute1 = new CyclingRoute("drieprovincieroute",
            "url1",
            "123", "3945");
    private CyclingRoute cyclingRoute2 = new CyclingRoute("Fietsen door de bomen",
            "url2",
            "456", "3980");
    private CyclingRoute cyclingRoute3 = new CyclingRoute("Fietsen door het water",
            "url3",
            "789", "3980");

    @BeforeEach
    public void beforeAllTests() {
        cyclingRouteRepository.deleteAll();
        cyclingRouteRepository.save(cyclingRoute1);
        cyclingRouteRepository.save(cyclingRoute2);
        cyclingRouteRepository.save(cyclingRoute3);
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void whengetroutes_thenreturnroutes() throws Exception {

        mockMvc.perform(get("/cyclingRoutes"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))

                .andExpect(jsonPath("$[0].name", is("drieprovincieroute")))
                .andExpect(jsonPath("$[0].img_url", is("url1")))
                .andExpect(jsonPath("$[0].routeCode", is("123")))
                .andExpect(jsonPath("$[0].postcode", is("3945")))

                .andExpect(jsonPath("$[1].name", is("Fietsen door de bomen")))
                .andExpect(jsonPath("$[1].img_url", is("url2")))
                .andExpect(jsonPath("$[1].routeCode", is("456")))
                .andExpect(jsonPath("$[1].postcode", is("3980")))

                .andExpect(jsonPath("$[2].name", is("Fietsen door het water")))
                .andExpect(jsonPath("$[2].img_url", is("url3")))
                .andExpect(jsonPath("$[2].routeCode", is("789")))
                .andExpect(jsonPath("$[2].postcode", is("3980")));

    }

    @Test
    public void getRoutesByname() throws Exception {
        mockMvc.perform(get("/cyclingRoutes/name/{name}", "drieprovincieroute"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("drieprovincieroute")))
                .andExpect(jsonPath("$[0].img_url", is("url1")))
                .andExpect(jsonPath("$[0].routeCode", is("123")))
                .andExpect(jsonPath("$[0].postcode", is("3945")));
    }

    @Test
    public void getRoutesByRouteCode() throws Exception {
        mockMvc.perform(get("/cyclingRoutes/code/{routeCode}", "789"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Fietsen door het water")))
                .andExpect(jsonPath("$.img_url", is("url3")))
                .andExpect(jsonPath("$.routeCode", is("789")))
                .andExpect(jsonPath("$.postcode", is("3980")));
    }

    @Test
    public void getRoutesByPostCode() throws Exception {
        mockMvc.perform(get("/cyclingRoutes/postcode/{postcode}", "3980"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Fietsen door de bomen")))
                .andExpect(jsonPath("$[0].img_url", is("url2")))
                .andExpect(jsonPath("$[0].routeCode", is("456")))
                .andExpect(jsonPath("$[0].postcode", is("3980")))

                .andExpect(jsonPath("$[1].name", is("Fietsen door het water")))
                .andExpect(jsonPath("$[1].img_url", is("url3")))
                .andExpect(jsonPath("$[1].routeCode", is("789")))
                .andExpect(jsonPath("$[1].postcode", is("3980")));
    }





}
