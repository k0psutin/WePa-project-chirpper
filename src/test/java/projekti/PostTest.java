package projekti;

import java.util.List;
import java.util.Map;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import projekti.model.Follow;
import projekti.model.Post;
import projekti.repository.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostTest {

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
    public void cantMakeAPostIfNotLoggedIn() throws Exception {

        mockMvc.perform(post("/profile/tester/post")
                .param("content", "Wohoo, first post!"))
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));

        MvcResult res = mockMvc.perform(get("/profile/tester")).andReturn();

        String content = res.getResponse().getContentAsString();
        Assert.assertFalse(content.contains("Wohoo, first post!"));
    }

    @Test
    @WithMockUser(username = "tester", password = "testtest", roles = "USER")
    public void canPostIfLoggedIn() throws Exception {
        mockMvc.perform(post("/profile/tester/post")
                .param("content", "Wohoo, first post!"));

        MvcResult res = mockMvc.perform(get("/profile/tester")).andReturn();

        String content = res.getResponse().getContentAsString();
        Assert.assertTrue(content.contains("Wohoo, first post!"));
    }
    
    @Test
    @WithMockUser(username = "tester", password = "testtest", roles = "USER")
    public void canLikeAPost() throws Exception {
        mockMvc.perform(post("/profile/tester/post")
                .param("content", "Wohoo, first post!"));

        MvcResult res = mockMvc.perform(get("/profile/tester")).andReturn();

        Map<String, Object> f = res.getModelAndView().getModel();
        List<Post> posts = (List<Post>) f.get("posts");
        Long id = posts.get(0).getId();
        mockMvc.perform(get("/profile/tester/post/" + id + "/like"));
        
        res = mockMvc.perform(get("/profile/tester")).andReturn();

        f = res.getModelAndView().getModel();
        posts = (List<Post>) f.get("posts");
        int likes = posts.get(0).getLikes().size();
        Assert.assertEquals("User can like a post", 1, likes);
    }
    
    @Test
    @WithMockUser(username = "tester", password = "testtest", roles = "USER")
    public void canLikeAPostOnce() throws Exception {
        mockMvc.perform(post("/profile/tester/post")
                .param("content", "Wohoo, first post!"));

        MvcResult res = mockMvc.perform(get("/profile/tester")).andReturn();

        Map<String, Object> f = res.getModelAndView().getModel();
        List<Post> posts = (List<Post>) f.get("posts");
        Long id = posts.get(0).getId();
        mockMvc.perform(get("/profile/tester/post/" + id + "/like"));
        mockMvc.perform(get("/profile/tester/post/" + id + "/like"));
        
        res = mockMvc.perform(get("/profile/tester")).andReturn();

        f = res.getModelAndView().getModel();
        posts = (List<Post>) f.get("posts");
        int likes = posts.get(0).getLikes().size();
        Assert.assertEquals("User can like a post once", 1, likes);
    }
    
    @Test
    @WithMockUser(username = "tester", password = "testtest", roles = "USER")
    public void showsOnly25Posts() throws Exception {
        for(int i = 0; i <= 30; i++) {
            mockMvc.perform(post("/profile/tester/post")
                .param("content", "Nr " + i + "!"));
        }

        MvcResult res = mockMvc.perform(get("/profile/tester")).andReturn();
        Map<String, Object> f = res.getModelAndView().getModel();
         List<Post> posts = (List<Post>) f.get("posts");
        Assert.assertEquals("Shows only 25 posts", 25, posts.size());
    }
}
