package com.sputnik.authorization;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthorizationControllerTest {
    @Mock
    AuthorizationService authorizationService;

    MockMvc mockMvc;

    @InjectMocks
    AuthorizationController controller;

    @Before
    public void setup() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testLoad() throws Exception {
        AuthorizationResponse authorizationResponse = new AuthorizationResponse();
        authorizationResponse.setAdmin(true);

        doReturn(authorizationResponse).when(authorizationService).getAuthorizationResponse(any(Authentication.class));

        mockMvc.perform(get("/authorization"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().string("{\"admin\":true}"));
    }
}