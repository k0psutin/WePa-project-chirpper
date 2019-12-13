package projekti;

import org.junit.*;
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

import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;
import projekti.model.Follow;
import projekti.repository.*;
import java.util.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FollowTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FollowRepository flwRep;

    @Autowired
    private AccountRepository accRep;

    @Autowired
    private PostRepository pstRep;

    @Before
    public void init() throws Exception {
        flwRep.deleteAllInBatch();
        accRep.deleteAllInBatch();
        pstRep.deleteAllInBatch();
        mockMvc.perform(post("/login/create")
                .param("firstName", "Test")
                .param("lastName", "Person")
                .param("username", "tester")
                .param("password", "testtest"));

        mockMvc.perform(post("/login/create")
                .param("firstName", "Test")
                .param("lastName", "Person")
                .param("username", "Mulder83")
                .param("password", "testtest"));
    }

    @Test
    @Transactional
    @WithMockUser(username = "tester", password = "testtest", roles = "USER")
    public void canFollowUser() throws Exception {
        mockMvc.perform(get("/profile/Mulder83/follow")).andExpect(status()
                .is3xxRedirection()).andExpect(redirectedUrl("/profile/Mulder83"));

        MvcResult res = mockMvc.perform(get("/profile/follows")).andReturn();

        Map<String, Object> f = res.getModelAndView().getModel();
        List<Follow> follows = (List<Follow>) f.get("following");
        Assert.assertTrue("Can follow users", follows.get(0).getFollowing().getUsername().equals("Mulder83"));
    }

    @Test
    @Transactional
    @WithMockUser(username = "tester", password = "testtest", roles = "USER")
    public void canBlockFollower() throws Exception {
        mockMvc.perform(get("/profile/Mulder83/follow")).andExpect(status()
                .is3xxRedirection()).andExpect(redirectedUrl("/profile/Mulder83"));

        mockMvc.perform(get("/profile/follow/block/Mulder83")).andExpect(status()
                .is3xxRedirection()).andExpect(redirectedUrl("/profile/follows"));

        MvcResult res = mockMvc.perform(get("/profile/follows")).andReturn();

        Map<String, Object> f = res.getModelAndView().getModel();
        List<Follow> follows = (List<Follow>) f.get("following");
        Assert.assertTrue("User is blocked", follows.get(0).getBlocked());
    }
}
