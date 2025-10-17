package com.wam.lab1_maintenance;

import com.wam.lab1_maintenance.config.JwtService;
import com.wam.lab1_maintenance.model.Credential;
import com.wam.lab1_maintenance.model.Role;
import com.wam.lab1_maintenance.model.User;
import com.wam.lab1_maintenance.repository.CredentialRepository;
import com.wam.lab1_maintenance.repository.UserRepository;
import com.wam.lab1_maintenance.request.AuthReq;
import com.wam.lab1_maintenance.request.AuthRes;
import com.wam.lab1_maintenance.request.RegisterReq;
import com.wam.lab1_maintenance.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link AuthService}.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        // Mock dependencies that do external work (e.g., authentication, JWT)
        AuthenticationManager mockAuthManager = mock(AuthenticationManager.class);
        JwtService mockJwtService = mock(JwtService.class);

        // Return fake token when generateToken() is called
        when(mockJwtService.generateToken(any(User.class))).thenReturn("fake-jwt-token");

        this.authService = new AuthService(
                userRepository,
                credentialRepository,
                passwordEncoder,
                mockJwtService,
                mockAuthManager
        );
    }

    @Test
    void register_ShouldCreateUserAndReturnToken() {
        // given
        RegisterReq req = new RegisterReq("John", "Doe", "johndoe", 22, "pass123");

        // when
        AuthRes res = authService.register(req);

        // then
        assertThat(res).isNotNull();
        assertThat(res.token()).isEqualTo("fake-jwt-token");

        Credential savedCred = credentialRepository.findByUsername("johndoe").orElse(null);
        assertThat(savedCred).isNotNull();
        assertThat(passwordEncoder.matches("password123", savedCred.getPassword())).isFalse();

        User savedUser = userRepository.findByCredentialUsername("johndoe").orElse(null);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getFname()).isEqualTo("John");
        assertThat(savedUser.getLname()).isEqualTo("Doe");
        assertThat(savedUser.getRole()).isEqualTo(Role.USER);
    }

    @Test
    void authenticate_ShouldReturnToken_WhenUserExists() {
        // given
        Credential cred = credentialRepository.save(Credential.builder()
                .username("alice")
                .password(passwordEncoder.encode("mypassword"))
                .isActive(true)
                .build());

        User user = userRepository.save(User.builder()
                .fname("Alice")
                .lname("Smith")
                .credential(cred)
                .role(Role.USER)
                .build());

        AuthReq req = new AuthReq("alice", "mypassword");

        // Mock behavior of authenticationManager and jwtService
        AuthenticationManager mockAuthManager = mock(AuthenticationManager.class);
        JwtService mockJwtService = mock(JwtService.class);
        when(mockJwtService.generateToken(user)).thenReturn("auth-jwt-token");

        AuthService testAuthService = new AuthService(
                userRepository,
                credentialRepository,
                passwordEncoder,
                mockJwtService,
                mockAuthManager
        );

        // when
        AuthRes res = testAuthService.authenticate(req);

        // then
        assertThat(res).isNotNull();
        assertThat(res.token()).isEqualTo("auth-jwt-token");
    }

    @Test
    void authenticate_ShouldThrowException_WhenUserNotFound() {
        AuthReq req = new AuthReq("ghost", "doesntmatter");

        assertThatThrownBy(() -> authService.authenticate(req))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("Username not found");
    }
}

