package com.seoulog.user.service;

import com.seoulog.user.config.auth.PrincipalDetails;
import com.seoulog.user.entity.User;
import com.seoulog.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String uniqueId) {
        User userEntity = userRepository.findOneWithAuthoritiesByEmail(uniqueId);

        if (userEntity == null) {
            userEntity = userRepository.findByOauthId(uniqueId);
            if (userEntity == null) {
                return null;
            } else {
                return new PrincipalDetails(userEntity); //userEntity를 넣어줘야 UserDetails에서 우리의 User 객체를 사용할 수 있음
            }
        }
        return new PrincipalDetails(userEntity); //userEntity를 넣어줘야 UserDetails에서 우리의 User 객체를 사용할 수 있음
    }

    private PrincipalDetails createUser(String username, User user) {
        if (!user.isActivated()) {
            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
        }

        return new PrincipalDetails(user);
    }
}