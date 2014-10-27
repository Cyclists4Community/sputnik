package com.sputnik.user;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest extends TestCase {

    @Mock
    User user;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Before
    public void setup() {
        doReturn("old@example.com").when(user).getEmail();
        doReturn(user).when(userRepository).findOne(45L);
    }

    @Test
    public void testUpdateEmail() throws Exception {
        userService.updateEmail("45", "new@example.com");

        verify(user).setEmail("new@example.com");
        verify(userRepository).save(user);
    }

    @Test
    public void testUpdateEmailNoChange() throws Exception {
        userService.updateEmail("45", "old@example.com");

        verify(user, never()).setEmail(anyString());
        verify(userRepository, never()).save(user);
    }
}