package com.seoulog.user.oauth.kakao;

import com.seoulog.user.oauth.OauthApiClient;
import com.seoulog.user.oauth.OauthLoginRequest;
import com.seoulog.user.oauth.OauthProfileResponse;
import com.seoulog.user.dto.TokenDto;
import com.seoulog.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class KakaoApiClient implements OauthApiClient {

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String authUrl;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String apiUrl;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUrl;
    private final RestTemplate restTemplate;
    public KakaoApiClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }


    @Override
    public TokenDto getOauthAccessToken(OauthLoginRequest oauthLoginRequest) {

        HttpHeaders httpHeaders = newHttpHeaders();

        MultiValueMap<String, String> body = oauthLoginRequest.makeBody();
        body.add("redirect_uri", "http://localhost:3000/register/" + redirectUrl);
        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<KakaoToken> response = restTemplate.postForEntity(authUrl, request, KakaoToken.class);

        return new TokenDto(Objects.requireNonNull(response.getBody()).getAccessToken(), response.getBody().getRefreshToken());
    }

    @Override
    public TokenDto getLoginOauthAccessToken(OauthLoginRequest oauthLoginRequest) {
        HttpHeaders httpHeaders = newHttpHeaders();

        MultiValueMap<String, String> body = oauthLoginRequest.makeBody();
        body.add("redirect_uri", "http://localhost:3000/login/" + redirectUrl);
        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<KakaoToken> response = restTemplate.postForEntity(authUrl, request, KakaoToken.class);

        return new TokenDto(Objects.requireNonNull(response.getBody()).getAccessToken(), response.getBody().getRefreshToken());
    }

    @Override
    public OauthProfileResponse getOauthProfile(String accessToken) {

        HttpHeaders httpHeaders = newHttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        // Request entity 생성
        HttpEntity<?> userInfoEntity = new HttpEntity<>(httpHeaders);

// Post 방식으로 Http 요청
// 응답 데이터 형식은 Hashmap 으로 지정
        ResponseEntity<KakaoMyInfo> response = restTemplate.postForEntity(apiUrl, userInfoEntity, KakaoMyInfo.class);
        return response.getBody();
    }

    @Override
    public User.Type getUserType() {
        return User.Type.KAKAO;
    }

    private HttpHeaders newHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return httpHeaders;
    }
}
