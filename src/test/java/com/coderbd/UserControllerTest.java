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
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @LocalServerPort
    private Integer port;

    private @Autowired
    MockMvc mvc;

    @Test
    @WithMockUser(username = "sami")
    public void should_give_Im_Used_226_USER_When_Data_Exist() throws Exception {

        ResultActions response =  mvc.perform(post("/api/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"username\": \"wong500\",\n" +
                        " \"password\":\"12345678\",\n" +
                        "\"email\":\"wong500@gmail.com\",\n" +
                        "\"mobile\":\"1655454590\"\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isImUsed());
    }
    //Provide New Data To Test This Test and Uncomment
    /*
    @Test
    public void should_give_Im_OK_User_When_Data_is_NEW() throws Exception {
        mvc.perform(post("/api/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"username\": \"wong600\",\n" +
                        " \"password\":\"12345678\",\n" +
                        "\"email\":\"wong600@gmail.com\",\n" +
                        "\"mobile\":\"1655454590\"\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    */


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

    @Test
    @DisplayName("Should be able to access private endpoint User By ID with auth")
    @WithMockUser(username = "sami")
    void shouldBeAbleToAccessPrivateEndpointUserByIdWithAuth() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("http://localhost:" + port + "/api/user/1"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        assertThat(response.getContentAsString()).isNotEmpty();
    }
    @Test
    @DisplayName("Should not be able to access private endpoint User By ID without auth")
    void shouldNotBeAbleToAccessPrivateEndpointUserByIdWithOutAuth() throws Exception {
       mvc.perform(get("http://localhost:" + port + "/api/user/1"))
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse();

    }
    @Test
    @DisplayName("Should get NOT FOUND to access private endpoint without ID with auth")
    @WithMockUser(username = "sami")
    void shouldGetNOtFound_404_ToAccessPrivateEndpointWithoutIdIdWithOutAuth() throws Exception {
        mvc.perform(get("http://localhost:" + port + "/api/user/"))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

    }
}
