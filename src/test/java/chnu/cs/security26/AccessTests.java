package chnu.cs.security26;

/*
    @author     owner
    @project    security26
    @class      AccessTests
    @version    1.0.0
    @since      21.11.2026 - 23:58.38
*/


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class AccessTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    // 1. Анонім -> /api/v1/books -> 401 (anyRequest().authenticated())
    @Test
    @WithAnonymousUser
    public void whenAnonymThenStatusUnauthorized() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/v1/books"));

        var status = result.andReturn().getResponse().getStatus();
        var message = result.andReturn().getResponse().getErrorMessage();
        assertEquals(401, status);
        assertNotNull(message);
        assertEquals("Unauthorized", message);
    }

    // 2. ADMIN -> /hello/admin -> 200 "Hello Admin!"
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    public void whenAuthenticatedThenStatusOk() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/v1/books/hello/admin"));

        var status = result.andReturn().getResponse().getStatus();
        var content = result.andReturn().getResponse().getContentAsString();
        assertEquals(200, status);
        assertEquals("Hello Admin!", content);
    }

    // 3. ADMIN -> /hello/user -> 403 (hasRole('USER'))
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    public void whenAuthenticatedThenStatus403() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/v1/books/hello/user"));

        var status = result.andReturn().getResponse().getStatus();
        var message = result.andReturn().getResponse().getErrorMessage();
        assertEquals(403, status);
        assertEquals("Forbidden", message);
    }

    // 4. USER -> /hello/user -> 200 "Hello User!"
    @Test
    @WithMockUser(username = "user", password = "user", roles = {"USER"})
    public void whenUserGetsHelloUserThenStatusOk() throws Exception {
        mockMvc.perform(get("/api/v1/books/hello/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello User!"));
    }

    // 5. USER -> /hello/admin -> 403 (hasRole('ADMIN'))
    @Test
    @WithMockUser(username = "user", password = "user", roles = {"USER"})
    public void whenUserGetsHelloAdminThenStatusForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/books/hello/admin"))
                .andExpect(status().isForbidden());
    }

    // 6. SUPERADMIN -> /hello/man -> 200 "Hello, man!" (hasAnyRole('ADMIN', 'SUPERADMIN'))
    @Test
    @WithMockUser(username = "superadmin", password = "superadmin", roles = {"SUPERADMIN"})
    public void whenSuperadminGetsHelloManThenStatusOk() throws Exception {
        mockMvc.perform(get("/api/v1/books/hello/man"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, man!"));
    }

    // 7. USER -> /hello/man -> 403 (роль USER не входить у ADMIN/SUPERADMIN)
    @Test
    @WithMockUser(username = "user", password = "user", roles = {"USER"})
    public void whenUserGetsHelloManThenStatusForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/books/hello/man"))
                .andExpect(status().isForbidden());
    }

    // 8. Анонім -> /hello/admin -> 401, а не 403 (автентифікація перевіряється раніше за @PreAuthorize)
    @Test
    @WithAnonymousUser
    public void whenAnonymGetsHelloAdminThenStatusUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/books/hello/admin"))
                .andExpect(status().isUnauthorized());
    }

    // 9. Реальний Basic Auth (user/user з InMemoryUserDetailsManager) -> /hello/user -> 200
    @Test
    public void whenHttpBasicValidCredentialsThenStatusOk() throws Exception {
        mockMvc.perform(get("/api/v1/books/hello/user").with(httpBasic("user", "user")))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello User!"));
    }

    // 10. Basic Auth з неправильним паролем -> 401
    @Test
    public void whenHttpBasicWrongPasswordThenStatusUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/books/hello/admin").with(httpBasic("admin", "wrongpassword")))
                .andExpect(status().isUnauthorized());
    }

    // 11. ADMIN -> /hello/man -> 200 "Hello, man!" (hasAnyRole('ADMIN', 'SUPERADMIN'))
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    public void whenAdminGetsHelloManThenStatusOk() throws Exception {
        mockMvc.perform(get("/api/v1/books/hello/man"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, man!"));
    }

    // 12. SUPERADMIN -> /hello/admin -> 403 (ролі не ієрархічні: SUPERADMIN != ADMIN)
    @Test
    @WithMockUser(username = "superadmin", password = "superadmin", roles = {"SUPERADMIN"})
    public void whenSuperadminGetsHelloAdminThenStatusForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/books/hello/admin"))
                .andExpect(status().isForbidden());
    }

    // 13. SUPERADMIN -> /hello/user -> 403 (hasRole('USER'))
    @Test
    @WithMockUser(username = "superadmin", password = "superadmin", roles = {"SUPERADMIN"})
    public void whenSuperadminGetsHelloUserThenStatusForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/books/hello/user"))
                .andExpect(status().isForbidden());
    }

    // 14. USER -> /hello -> 200 "Hello!" (без @PreAuthorize достатньо автентифікації)
    @Test
    @WithMockUser(username = "user", password = "user", roles = {"USER"})
    public void whenUserGetsHelloThenStatusOk() throws Exception {
        mockMvc.perform(get("/api/v1/books/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello!"));
    }

    // 15. Анонім -> /hello -> 401
    @Test
    @WithAnonymousUser
    public void whenAnonymGetsHelloThenStatusUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/books/hello"))
                .andExpect(status().isUnauthorized());
    }

    // 16. SUPERADMIN -> /hello/unknown -> 200 "Hello Unknown!" (будь-яка роль після автентифікації)
    @Test
    @WithMockUser(username = "superadmin", password = "superadmin", roles = {"SUPERADMIN"})
    public void whenSuperadminGetsHelloUnknownThenStatusOk() throws Exception {
        mockMvc.perform(get("/api/v1/books/hello/unknown"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Unknown!"));
    }

    // 17. Basic Auth неіснуючого користувача -> 401
    @Test
    public void whenHttpBasicUnknownUserThenStatusUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/books/hello/user").with(httpBasic("guest", "guest")))
                .andExpect(status().isUnauthorized());
    }

    // 18. Анонім -> /index.html -> 200 (permitAll)
    @Test
    @WithAnonymousUser
    public void whenAnonymGetsIndexHtmlThenStatusOk() throws Exception {
        mockMvc.perform(get("/index.html"))
                .andExpect(status().isOk());
    }

    // 19. Відповідь 401 містить заголовок WWW-Authenticate зі схемою Basic
    @Test
    @WithAnonymousUser
    public void whenUnauthorizedThenWwwAuthenticateHeaderIsBasic() throws Exception {
        mockMvc.perform(get("/api/v1/books/hello/user"))
                .andExpect(status().isUnauthorized())
                .andExpect(header().string("WWW-Authenticate", startsWith("Basic")));
    }

    // 20. Basic Auth user/user -> GET /api/v1/books -> 200 і JSON
    @Test
    public void whenHttpBasicUserGetsBooksThenStatusOkAndJson() throws Exception {
        mockMvc.perform(get("/api/v1/books").with(httpBasic("user", "user")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
