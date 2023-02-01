package com.agleveratto.superhero.infrastructure.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class UserUtilsTest {

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
    void findUserByEmail_givenACorrectMail_thenReturnUserDetails(){
        assertThat(new UserUtils().findUserByEmail("agleveratto@gmail.com")).isEqualTo(userDetails);
    }

    @Test
    void findUserByEmail_givenAIncorrectMail_thenThrowUsernameNotFoundException(){
        assertThatThrownBy(() -> new UserUtils().findUserByEmail("agleveratto")).isInstanceOf(UsernameNotFoundException.class);
    }
}
