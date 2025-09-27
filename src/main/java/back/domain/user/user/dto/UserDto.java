package back.domain.user.user.dto;

import back.domain.user.user.entity.User;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public record UserDto (
        @NonNull long id,
        @NonNull LocalDateTime createDate,
        @NonNull LocalDateTime modifyDate,
        @NonNull String nickname,
        @NonNull boolean isAdmin
){
    public UserDto(User user){
        this(
                user.getId(),
                user.getCreateDate(),
                user.getModifyDate(),
                user.getNickname(),
                user.isAdmin()
        );
    }
}
