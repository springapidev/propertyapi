package com.coderbd;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class PropertyControllerTest {

    @LocalServerPort
    private Integer port;

    private @Autowired
    MockMvc mvc;



    @Test
    @WithMockUser(username = "sami")
    public void should_give_bad_request_400_When_with_auth_but_without_data() throws Exception {
        MockHttpServletResponse response = mvc.perform(post("http://localhost:" + port + "/api/property/add"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();
        assertThat(response.getContentAsString()).isNotEmpty();
    }
    @Test
    public void should_give_bad_request_401_When_with_auth_but_without_auth() throws Exception {
        mvc.perform(post("/api/property/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"country\": \"Bangladesh\",\n" +
                        "  \"description\": \"House Property\",\n" +
                        "  \"price\": 20000.0,\n" +
                        "  \"status\": \"NEW\",\n" +
                        "  \"title\": \"Tokyo Land Rover Area\"\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "sami")
    public void should_give_created_201_When_with_auth_but_with_auth() throws Exception {
        mvc.perform(post("/api/property/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"country\": \"Bangladesh\",\n" +
                        "  \"description\": \"House Property\",\n" +
                        "  \"price\": 20000.0,\n" +
                        "  \"status\": \"NEW\",\n" +
                        "  \"title\": \"Tokyo Land Rover Area\"\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    @DisplayName("Should get forbidden on private endpoint without auth")
    void shouldGetForbiddenOnPrivateEndpointWithoutAuth() throws Exception {
        mvc.perform(get("http://localhost:" + port + "/api/property/list"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @DisplayName("Should be able to access private endpoint with auth")
    @WithMockUser(username = "sami")
    void shouldBeAbleToAccessPrivateEndpointWithAuth() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("http://localhost:" + port + "/api/property/list"))
                .andExpect(status().isFound())
                .andReturn().getResponse();

        assertThat(response.getContentAsString()).isNotEmpty();
    }


    @Test
    @DisplayName("Should be able to approve Property endpoint By ADMIN")
    @WithMockUser(username = "boss")// here boss is an admin
    void shouldBeAbleToApprovePrivateEndpointWithoutAdmin() throws Exception {
        MockHttpServletResponse response = mvc.perform(post("http://localhost:" + port + "/api/property/approved/1"))
                .andExpect(status().isAccepted())
                .andReturn().getResponse();

    }
    @Test
    @DisplayName("Should not be able to approve Property endpoint without ADMIN")
    @WithMockUser(username = "sami") // here sami is a normal user who have no permission to approve
    void shouldNotBeAbleToApprovePrivateEndpointWithoutAdmin() throws Exception {
        MockHttpServletResponse response = mvc.perform(post("http://localhost:" + port + "/api/property/approve/1"))
                .andExpect(status().isForbidden())
                .andReturn().getResponse();

    }
}
