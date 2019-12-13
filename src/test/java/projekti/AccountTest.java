package projekti;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.context.WebApplicationContext;
import projekti.repository.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accRep;

    @Autowired
    private PostRepository pstRep;

    @Before
    public void init() throws Exception {
        accRep.deleteAllInBatch();
        pstRep.deleteAllInBatch();
        mockMvc.perform(post("/login/create")
                .param("firstName", "Test")
                .param("lastName", "Person")
                .param("username", "tester")
                .param("password", "testtest"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void canCreateAccount() throws Exception {
        accRep.deleteAllInBatch();
        mockMvc.perform(post("/login/create")
                .param("firstName", "Test")
                .param("lastName", "Person")
                .param("username", "tester")
                .param("password", "testtest"))
                .andExpect(status().is3xxRedirection());;
    }

    @Test
    public void loginWithInvalidUserIsUnauthenticated() throws Exception {
        FormLoginRequestBuilder login = formLogin()
                .user("invalid")
                .password("invalidpassword");

        mockMvc.perform(login)
                .andExpect(unauthenticated()).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login-error"));
    }

    @Test
    @WithMockUser(username = "tester", password = "testtest", roles = "USER")
    public void canLoginWithValidUser() throws Exception {
        FormLoginRequestBuilder login = formLogin()
                .user("tester")
                .password("testtest");

        mockMvc.perform(login)
                .andExpect(authenticated().withUsername("tester")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = "tester", password = "testtest", roles = "USER")
    public void canSearchUsers() throws Exception {
        mockMvc.perform(post("/login/create")
                .param("firstName", "Test")
                .param("lastName", "Person")
                .param("username", "Mulder83")
                .param("password", "testtest"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(post("/profile/search/")
                .param("user", "Mulder83"))
                .andExpect(status()
                        .is3xxRedirection()).andExpect(redirectedUrl("/profile/Mulder83"));
    }
}
