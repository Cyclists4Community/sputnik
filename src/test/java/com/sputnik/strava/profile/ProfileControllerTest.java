package com.sputnik.strava.profile;

import com.sputnik.strava.StravaService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileControllerTest {
    @Mock
    StravaService stravaService;

    MockMvc mockMvc;

    @InjectMocks
    ProfileController controller;

    @Before
    public void setup() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testLoad() throws Exception {
        mockMvc.perform(get("/strava/profile"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProfile() throws Exception {

        AthleteProfile profile = new AthleteProfile("freddy@example.com", "Fred Derf");

        doReturn(profile).when(stravaService).getAthleteProfile();

        mockMvc.perform(get("/strava/profile"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().string("{\"email\":\"freddy@example.com\",\"name\":\"Fred Derf\"}"));
    }
}
