package back.domain.post.postComment.entity;

import back.domain.post.post.entity.Post;
import back.domain.user.user.entity.User;
import back.global.exception.ServiceException;
import back.global.jpa.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class PostComment extends BaseEntity {
    @ManyToOne
    private User author;

    private String content;

    @JsonIgnore
    @ManyToOne
    private Post post;

    public PostComment(User author, Post post, String content) {
        this.author = author;
        this.post = post;
        this.content = content;
    }

    public void checkActorCanDelete(User actor) {
        if (!actor.getUsername().equals(author.getUsername())) {
            throw new ServiceException("403-1", "%d번 댓글 삭제 권한이 없습니다.".formatted(getId()));
        }
    }

    public void checkActorCanModify(User actor) {
        if (!actor.getUsername().equals(author.getUsername())) {
            throw new ServiceException("403-1", "%d번 댓글 수정 권한이 없습니다.".formatted(getId()));
        }
    }

    public void modify(String content) {
        this.content = content;
    }
}
