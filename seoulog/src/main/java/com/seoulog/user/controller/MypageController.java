package com.seoulog.user.controller;

import com.seoulog.common.annotation.CurrentUser;
import com.seoulog.user.dto.MypageResponseDto;
import com.seoulog.user.dto.UserDto;
import com.seoulog.user.entity.User;
import com.seoulog.user.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    /**
     * 회원 정보 조회
     */
    @GetMapping("/mypage")
    public ResponseEntity<MypageResponseDto> getUserInfo(@CurrentUser User user) {

        MypageResponseDto mypageResponseDto = new MypageResponseDto();
        mypageService.getUserInfo(user, mypageResponseDto);
        mypageService.getTeamList(user, mypageResponseDto);
        return ResponseEntity.ok(mypageResponseDto);
    }

    /**
     * 회원 정보 변경
     */
    @PatchMapping("/mypage/nickname")
    public ResponseEntity<String> updateUserNickname(@CurrentUser User user, @RequestBody UserDto userDto) {
        mypageService.updateUserNickname(user, userDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/mypage/password")
    public ResponseEntity<String> updateUserPW(@CurrentUser User user, @RequestBody UserDto userDto) {
        mypageService.updatePW(user, userDto);
        return ResponseEntity.ok().build();
    }
}
