package com.seoulog.user.oauth.kakao;

import com.seoulog.user.oauth.OauthApiClient;
import com.seoulog.user.oauth.OauthInfo;
import com.seoulog.user.oauth.OauthProfileResponse;
import com.seoulog.user.dto.TokenDto;
import com.seoulog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoOauthService {
    private final OauthApiClient kakaoApiClient;
    private final UserRepository userRepository;
    public OauthInfo getKakaoInfo(KakaoLoginRequest kakaoLoginRequest) {
        TokenDto kakaoToken = kakaoApiClient.getOauthAccessToken(kakaoLoginRequest);
        OauthProfileResponse oauthProfile = kakaoApiClient.getOauthProfile(kakaoToken.getAccessToken());
        System.out.println("카카오 프로필 : " + oauthProfile);

//        UserDto kakaoUser = principalDetails.getUserDto();
        //아직 회원가입을 하지 않았다면 회원가입 진행
        //        userRepository.save(user);
//        log.info("save User={}", kakaoUser);

        return OauthInfo.builder()
                .refreshToken(kakaoToken.getRefreshToken())
                .type(kakaoLoginRequest.userType())
                .nickname(oauthProfile.getNickName())
                .id(oauthProfile.getId())
                .build();
    }

    public OauthInfo getKakaoLoginInfo(KakaoLoginRequest kakaoLoginRequest) {
        TokenDto kakaoToken = kakaoApiClient.getLoginOauthAccessToken(kakaoLoginRequest);
        OauthProfileResponse oauthProfile = kakaoApiClient.getOauthProfile(kakaoToken.getAccessToken());
        System.out.println("카카오 프로필 : " + oauthProfile);

//        UserDto kakaoUser = principalDetails.getUserDto();
        //아직 회원가입을 하지 않았다면 회원가입 진행
        //        userRepository.save(user);
//        log.info("save User={}", kakaoUser);

        return OauthInfo.builder()
                .refreshToken(kakaoToken.getRefreshToken())
                .type(kakaoLoginRequest.userType())
                .nickname(oauthProfile.getNickName())
                .id(oauthProfile.getId())
                .build();
    }

}
