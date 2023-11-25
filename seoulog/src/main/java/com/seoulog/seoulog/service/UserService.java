package com.seoulog.seoulog.service;

import com.seoulog.seoulog.config.auth.PrincipalDetails;
import com.seoulog.seoulog.dto.LoginDto;
import com.seoulog.seoulog.dto.TokenDto;
import com.seoulog.seoulog.dto.UserDto;
import com.seoulog.seoulog.entity.User;
import com.seoulog.seoulog.jwt.TokenProvider;
import com.seoulog.seoulog.repository.RefreshTokenRepository;
import com.seoulog.seoulog.repository.UserRepository;
//import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public User signup(UserDto userDto) { //회원가입 메소드
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        User user = User.builder()
                .password(encoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .email(userDto.getEmail())
                .activated(true)
                .type(User.Type.NATIVE)
                .build();

        return userRepository.save(user);
    }

}