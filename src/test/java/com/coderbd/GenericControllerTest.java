package com.coderbd;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class GenericControllerTest {
    @LocalServerPort
    private Integer port;

    private @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Should be able to access public endpoint without auth")
    void shouldBeAbleToAccessPublicEndpointWithoutAuth() throws Exception {
        MockHttpServletResponse response = mvc.perform(post("http://localhost:" + port + "/api/user/create"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();
        assertThat(response.getContentAsString()).isNotEmpty();
    }


    @Test
    @DisplayName("Should get forbidden on private endpoint without auth")
    void shouldGetForbiddenOnPrivateEndpointWithoutAuth() throws Exception {
        mvc.perform(get("http://localhost:" + port + "/api/user/list"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @DisplayName("Should be able to access private endpoint with auth")
    @WithMockUser(username = "sami")
    void shouldBeAbleToAccessPrivateEndpointWithAuth() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("http://localhost:" + port + "/api/user/list"))
                .andExpect(status().isFound())
                .andReturn().getResponse();

        assertThat(response.getContentAsString()).isNotEmpty();
    }

}