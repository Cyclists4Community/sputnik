package com.sputnik.user;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileControllerTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    ProfileController profileController;

    MockMvc mockMvc;

    @Before
    public void setup() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();
    }

    @Test
    public void testGetProfile() throws Exception {
        User user = new User("test@example.com");

        doReturn(user).when(userRepository).findOne(47L);

        Principal principal = mock(Principal.class);
        doReturn("47").when(principal).getName();

        mockMvc.perform(get("/profile").principal(principal))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().string("{\"email\":\"test@example.com\"}"));
    }
}