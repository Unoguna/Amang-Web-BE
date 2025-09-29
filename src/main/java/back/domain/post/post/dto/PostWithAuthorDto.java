package back.domain.post.post.dto;

import back.domain.post.post.entity.Post;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public record PostWithAuthorDto(
        @NonNull long id,
        @NonNull LocalDateTime createDate,
        @NonNull LocalDateTime modifyDate,
        @NonNull long authorId,
        @NonNull String authorName,
        @NonNull String title,
        @NonNull String content
) {
    public PostWithAuthorDto(Post post) {
        this(
                post.getId(),
                post.getCreateDate(),
                post.getModifyDate(),
                post.getAuthor().getId(),
                post.getAuthor().getNickname(),
                post.getTitle(),
                post.getContent()
        );
    }
}