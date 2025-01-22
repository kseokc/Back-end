package icurriculum.global.security.controller;

import icurriculum.global.response.ApiResponse;
import icurriculum.global.response.status.ErrorStatus;
import icurriculum.global.security.service.redis.RefreshTokenService;
import icurriculum.global.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/refresh")
    public ApiResponse<Map<String,String>> refreshAccessToken(@CookieValue("refresh-token") String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            return ApiResponse.onFailure(ErrorStatus.INVALID_TOKEN, Map.of("error", "RefreshToken이 유효하지 않습니다."));
        }

        if (!refreshTokenService.isValidRefreshToken(refreshToken)) {
            return ApiResponse.onFailure(ErrorStatus.INVALID_TOKEN, Map.of("error", "RefreshToken이 유효하지 않습니다."));
        }

        String newAccessToken = refreshTokenService.refreshAccessToken(refreshToken);
        return ApiResponse.onSuccess(Map.of("accessToken", newAccessToken));
    }
}