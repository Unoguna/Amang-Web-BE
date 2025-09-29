package back.domain.post.post.dto;

import back.domain.post.post.entity.Post;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public record PostDto(
        @NonNull long id,
        @NonNull LocalDateTime createDate,
        @NonNull LocalDateTime modifyDate,
        @NonNull String title,
        @NonNull String content
) {
    public PostDto(Post post) {
        this(
                post.getId(),
                post.getCreateDate(),
                post.getModifyDate(),
                post.getTitle(),
                post.getContent()
        );
    }
}