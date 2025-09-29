package back.domain.post.post.entity;

import back.domain.user.user.entity.User;
import back.global.exception.ServiceException;
import back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Post extends BaseEntity {
    @ManyToOne
    private User author;
    private String title;
    private String content;

    public void checkActorCanDelete(User actor) {
        if (!actor.getUsername().equals(author.getUsername())) {
            throw new ServiceException("403-1", "%d번 글 삭제 권한이 없습니다.".formatted(getId()));
        }
    }

    public Post(User author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public void modify(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void checkActorCanModify(User actor) {
        if (!actor.getUsername().equals(author.getUsername())) {
            throw new ServiceException("403-1", "%d번 글 수정 권한이 없습니다.".formatted(getId()));
        }
    }
}
