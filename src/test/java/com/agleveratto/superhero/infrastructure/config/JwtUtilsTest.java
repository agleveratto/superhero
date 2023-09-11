package com.agleveratto.superhero.infrastructure.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.StringJoiner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class JwtUtilsTest {

    public static final String VALID_USERNAME_JWT = "eyJhbGciOiJIUzI1NiJ9";
    public static final String VALID_PASSWORD_JWT = "eyJzdWIiOiJhZ2xldmVyYXR0b0BnbWFpbC5jb20iLCJleHAiOjE2OTQ0ODEyNTQsImlhdCI6MTY5NDM5NDg1NCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV19";
    public static final String VALID_DATE_JWT = "OS2zqgPGq21TIXWS6JafoUQtxUAWmRh2Lb0gWhltQEw";
    public static final String VALID_TOKEN = new StringJoiner(".")
            .add(VALID_USERNAME_JWT)
            .add(VALID_PASSWORD_JWT)
            .add(VALID_DATE_JWT)
            .toString();
    public static final String EXPIRED_PASSWORD_JWT = "eyJzdWIiOiJhZ2xldmVyYXR0b0BnbWFpbC5jb20iLCJleHAiOjE2NzQ1ODM0MzMsImlhdCI6MTY3NDQ5NzAzMywiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV19";
    public static final String EXPIRED_DATE_JWT = "whtDXwR3U76OPycPcN8C-T5bkL9VuIaUVn_XPaq-dWs";
    public static final String EXPIRED_TOKEN = new StringJoiner(".")
            .add(VALID_USERNAME_JWT)
            .add(EXPIRED_PASSWORD_JWT)
            .add(EXPIRED_DATE_JWT)
            .toString();
    public static final String INVALID_TOKEN = "a";
    @InjectMocks
    JwtUtils jwtUtils;

    static UserDetails userDetails;

    @BeforeAll
    static void setup(){
        userDetails = new User(
                "agleveratto@gmail.com",
                "password",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
    }

    @Test
    void extractUserName_givenInvalidToken_thenThrowMalformedJwtException(){
        assertThatThrownBy(() -> jwtUtils.extractUserName(INVALID_TOKEN)).isInstanceOf(MalformedJwtException.class);
    }

    @Test
    void extractUserName_givenExpiredToken_thenThrowExpiredJwtException(){
        assertThatThrownBy(() -> jwtUtils.extractUserName(EXPIRED_TOKEN))
                .isInstanceOf(ExpiredJwtException.class);
    }

    @Test
    void extractUserName_givenNewToken_thenReturnUsername(){
        assertThat(jwtUtils.extractUserName(VALID_TOKEN))
                .isNotBlank()
                .isEqualTo("agleveratto@gmail.com");
    }

    @Test
    void extractExpiration_givenAInvalidToken_thenThrowMalformedJwtException(){
        assertThatThrownBy(() -> jwtUtils.extractExpiration(INVALID_TOKEN)).isInstanceOf(MalformedJwtException.class);
    }

    @Test
    void extractExpiration_givenExpiredToken_thenThrowExpiredJwtException(){
        assertThatThrownBy(() -> jwtUtils.extractUserName(EXPIRED_TOKEN))
                .isInstanceOf(ExpiredJwtException.class);
    }

    @Test
    void extractExpiration_givenNewToken_thenReturnAValidDate(){
        assertThat(jwtUtils.extractExpiration(VALID_TOKEN))
                .isNotNull();
    }

    @Test
    void generateToken_givenValidUserDetails(){
        assertThat(jwtUtils.generateToken(userDetails)).isNotBlank();
    }

    @Test
    void isExpiredToken_givenNullUserDetails(){
        assertThatThrownBy(() -> jwtUtils.isTokenValid(VALID_TOKEN, null)).isInstanceOf(NullPointerException.class);
    }
    @Test
    void isExpiredToken_givenInvalidToken_thenThrowMalformedJwtException(){
        assertThatThrownBy(() -> jwtUtils.isTokenValid(INVALID_TOKEN, userDetails)).isInstanceOf(MalformedJwtException.class);
    }

    @Test
    void isExpiredToken_givenExpiredToken_thenReturnFalse(){
        assertThatThrownBy(() -> jwtUtils.isTokenValid(EXPIRED_TOKEN, userDetails))
                .isInstanceOf(ExpiredJwtException.class);
    }

    @Test
    void isExpiredToken_givenNewTokenWithUserDetails_thenReturnTrue(){
        assertThat(jwtUtils.isTokenValid(VALID_TOKEN, userDetails)).isTrue();
    }
}
