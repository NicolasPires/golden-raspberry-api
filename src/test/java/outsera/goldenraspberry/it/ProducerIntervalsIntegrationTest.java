package outsera.goldenraspberry.it;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProducerIntervalsIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnMinAndMaxIntervals() throws Exception {
        mockMvc.perform(get("/producers/intervals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min", hasSize(1)))
                .andExpect(jsonPath("$.max", hasSize(1)))

                .andExpect(jsonPath("$.min[0].producer", is("Producer A")))
                .andExpect(jsonPath("$.min[0].interval", is(1)))
                .andExpect(jsonPath("$.min[0].previousWin", is(1980)))
                .andExpect(jsonPath("$.min[0].followingWin", is(1981)))

                .andExpect(jsonPath("$.max[0].producer", is("Producer B")))
                .andExpect(jsonPath("$.max[0].interval", is(10)))
                .andExpect(jsonPath("$.max[0].previousWin", is(1990)))
                .andExpect(jsonPath("$.max[0].followingWin", is(2000)));
    }

    @Test
    void swaggerShouldBeAvailable() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Golden Raspberry API")));
    }
}
