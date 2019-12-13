package projekti;

import org.junit.*;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import projekti.model.*;
import projekti.repository.*;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostCommentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accRep;

    @Autowired
    private PostRepository pstRep;
    
    @Autowired
    private PostCommentRepository cmtRep;

    @Before
    public void init() throws Exception {
        cmtRep.deleteAllInBatch();
        pstRep.deleteAllInBatch();
        accRep.deleteAllInBatch();
        mockMvc.perform(post("/login/create")
                .param("firstName", "Test")
                .param("lastName", "Person")
                .param("username", "tester")
                .param("password", "testtest"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Transactional
    @WithMockUser(username = "tester", password = "testtest", roles = "USER")
    public void canCreateComments() throws Exception {
        mockMvc.perform(post("/profile/tester/post")
                .param("content", "Truth is out there!"))
                .andExpect(status()
                        .is3xxRedirection()).andReturn();

        MvcResult res = mockMvc.perform(get("/profile/tester")).andReturn();

        List<Post> posts = (List<Post>) res.getRequest().getAttribute("posts");

        Long id = posts.get(0).getId();

        mockMvc.perform(post("/profile/tester/post/" + id + "/comment")
                .param("comment", "Lol, x-files."))
                .andExpect(status()
                        .is3xxRedirection()).andReturn();

        Assert.assertTrue("Can find comment on post", cmtRep.findAll().get(0).getContent().equals("Lol, x-files."));
    }
}
