package back.domain.user.user.service;

import back.domain.user.user.entity.User;
import back.standard.util.Ut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthTokenService {
    @Value("${custom.jwt.secretKey}")
    private String jwtSecretKey;

    @Value("${custom.accessToken.expireSeconds}")
    private int accessTokenExpireSeconds;

    public String genAccessToken(User user) {
        long id = user.getId();
        String username = user.getUsername();
        String nickname = user.getNickname();

        Map<String, Object> claims = Map.of("id", id, "username", username, "nickname", nickname);

        return Ut.jwt.toString(
                jwtSecretKey,
                accessTokenExpireSeconds,
                claims
        );
    }
}
