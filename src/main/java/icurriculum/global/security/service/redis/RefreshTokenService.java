package icurriculum.global.security.service.redis;

import icurriculum.domain.member.RoleType;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import icurriculum.global.security.entity.RefreshToken;
import icurriculum.global.security.repository.RefreshTokenRepository;
import icurriculum.global.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new GeneralException(ErrorStatus.REFRESH_TOKEN_NOT_FOUND));
    }

    @Transactional
    public void saveRefreshToken(Long memberId, String refreshToken) {
        RefreshToken updatedRefreshToken = refreshTokenRepository.findByMemberId(memberId)
                .map(originalRefreshToken -> originalRefreshToken.update(refreshToken))
                .orElse(new RefreshToken(memberId, refreshToken));

        refreshTokenRepository.save(updatedRefreshToken);
    }

    @Transactional
    public void deleteRefreshToken(Long memberId) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(memberId);
        refreshToken.ifPresent(refreshTokenRepository::delete);
    }

    public boolean isValidRefreshToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token).isPresent();
    }

    public String refreshAccessToken(String token) {
        Long memberId = jwtUtil.getMemberId(token);
        String email = jwtUtil.getEmail(token);
        RoleType roleType = RoleType.valueOf(jwtUtil.getRoleType(token));

        return jwtUtil.createJwt(memberId, email, true, roleType);
    }
}
