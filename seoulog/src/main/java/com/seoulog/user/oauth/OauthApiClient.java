package com.seoulog.user.oauth;

import com.seoulog.user.dto.TokenDto;
import com.seoulog.user.entity.User;

public interface OauthApiClient {
    TokenDto getOauthAccessToken(OauthLoginRequest oauthLoginRequest);

    TokenDto getLoginOauthAccessToken(OauthLoginRequest oauthLoginRequest);

    OauthProfileResponse getOauthProfile(String accessToken);

    User.Type getUserType();
}
