package com.seoulog.user.oauth.naver;

import com.seoulog.user.oauth.OauthApiClient;
import com.seoulog.user.oauth.OauthInfo;
import com.seoulog.user.oauth.OauthProfileResponse;
import com.seoulog.user.dto.TokenDto;
import com.seoulog.user.repository.UserRepository;
import com.seoulog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverOauthService {
    private final OauthApiClient naverApiClient;
    private final UserService userService;
    private final UserRepository userRepository;


    public OauthInfo getNaverInfo(NaverLoginRequest naverLoginRequest) {
        TokenDto naverToken = naverApiClient.getOauthAccessToken(naverLoginRequest);
        OauthProfileResponse oauthProfile = naverApiClient.getOauthProfile(naverToken.getAccessToken());


        return OauthInfo.builder()
                .refreshToken(naverToken.getRefreshToken())
                .email(oauthProfile.getEmail())
                .type(naverLoginRequest.userType())
                .nickname(oauthProfile.getNickName())
                .id(oauthProfile.getId())
                .build();
    }

    public OauthInfo getNaverLoginInfo(NaverLoginRequest naverLoginRequest) {
        TokenDto naverToken = naverApiClient.getLoginOauthAccessToken(naverLoginRequest);
        OauthProfileResponse oauthProfile = naverApiClient.getOauthProfile(naverToken.getAccessToken());


        return OauthInfo.builder()
                .refreshToken(naverToken.getRefreshToken())
                .email(oauthProfile.getEmail())
                .type(naverLoginRequest.userType())
                .nickname(oauthProfile.getNickName())
                .id(oauthProfile.getId())
                .build();
    }

}
