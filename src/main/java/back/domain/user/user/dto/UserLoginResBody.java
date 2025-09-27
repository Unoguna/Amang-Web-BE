package back.domain.user.user.dto;

import org.springframework.lang.NonNull;

public record UserLoginResBody (
        @NonNull UserDto item,
        @NonNull String apiKey,
        @NonNull String accessToken
){
}
