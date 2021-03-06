package com.sputnik.authorization;

import com.sputnik.user.User;
import com.sputnik.user.UserRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationServiceTest extends TestCase {

    @Mock
    Authentication authentication;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AuthorizationService authorizationService;

    User currentUser;

    @Before
    public void setup() {
        currentUser = mock(User.class);
    }

    @Test
    public void testGetAuthoritiesReturnAdmin() throws Exception {
        doReturn(currentUser).when(userRepository).findOne(1L);

        doReturn(true).when(currentUser).getAdmin();

        List<GrantedAuthority> mockGrantedAuthorities = AuthorityUtils.createAuthorityList("ADMIN");

        List<GrantedAuthority> returnedAuthorities = authorizationService.getAuthorities("1");

        assertEquals(mockGrantedAuthorities, returnedAuthorities);
    }

    @Test
    public void testGetAuthoritiesReturnNoAuthorities() throws Exception {
        doReturn(currentUser).when(userRepository).findOne(1L);

        doReturn(false).when(currentUser).getAdmin();

        List<GrantedAuthority> mockGrantedAuthorities = AuthorityUtils.NO_AUTHORITIES;

        List<GrantedAuthority> returnedAuthorities = authorizationService.getAuthorities("1");

        assertEquals(mockGrantedAuthorities, returnedAuthorities);
    }

    @Test
    public void testGetAdminRole() throws Exception {
        String adminRole = authorizationService.getAdminRole();
        assertEquals("ADMIN", adminRole);
    }

    @Test
    public void authoritiesAdminTrue() {
        List<GrantedAuthority> mockGrantedAuthorities = AuthorityUtils.createAuthorityList("ADMIN", "USER");
        doReturn(mockGrantedAuthorities).when(authentication).getAuthorities();

        assertEquals(true, authorizationService.getAuthorizationResponse(authentication).isAdmin());
    }

    @Test
    public void authoritiesAdminFalse() {
        List<GrantedAuthority> mockGrantedAuthorities = AuthorityUtils.createAuthorityList("OTHER", "USER");
        doReturn(mockGrantedAuthorities).when(authentication).getAuthorities();

        assertEquals(false, authorizationService.getAuthorizationResponse(authentication).isAdmin());
    }

    @Test
    public void authoritiesEmpty() {
        List<GrantedAuthority> mockGrantedAuthorities = AuthorityUtils.createAuthorityList();
        doReturn(mockGrantedAuthorities).when(authentication).getAuthorities();

        assertEquals(false, authorizationService.getAuthorizationResponse(authentication).isAdmin());
    }
}