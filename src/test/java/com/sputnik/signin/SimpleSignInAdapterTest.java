package com.sputnik.signin;

import com.sputnik.authorization.AuthorizationService;
import com.sputnik.user.UserService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest( { SignInUtils.class })
public class SimpleSignInAdapterTest extends TestCase {

    @Mock
    NativeWebRequest request;

    @Mock
    RequestCache requestCache;

    @Mock
    UserService userService;

    @Mock
    AuthorizationService authorizationService;

    @InjectMocks
    SimpleSignInAdapter simpleSignInAdapter;

    @org.junit.Before
    public void setup() {
        HttpServletRequest nativeRequest = mock(HttpServletRequest.class);
        HttpServletResponse nativeResponse = mock(HttpServletResponse.class);

        doReturn(nativeRequest).when(request).getNativeRequest(HttpServletRequest.class);
        doReturn(nativeResponse).when(request).getNativeRequest(HttpServletResponse.class);

        doReturn(null).when(requestCache).getRequest(nativeRequest, nativeResponse);

        mockStatic(SignInUtils.class);
        PowerMockito.spy(SignInUtils.class);
    }

    @Test
    public void testSignIn() throws Exception {
        String localUserId = "14";
        String email = "test@example.com";

        Connection connection = mock(Connection.class);
        UserProfile userProfile = mock(UserProfile.class);
        doReturn(userProfile).when(connection).fetchUserProfile();
        doReturn(email).when(userProfile).getEmail();

        List<GrantedAuthority> grantedAuthorityList = AuthorityUtils.createAuthorityList();
        doReturn(grantedAuthorityList).when(authorizationService).getAuthorities(localUserId);

        simpleSignInAdapter.signIn(localUserId, connection, request);

        verify(userService).updateEmail("14", "test@example.com");
    }
}