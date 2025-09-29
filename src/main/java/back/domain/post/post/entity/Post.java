package back.domain.post.post.entity;

import back.domain.post.postComment.entity.PostComment;
import back.domain.user.user.entity.User;
import back.global.exception.ServiceException;
import back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor
@Getter
@Entity
public class Post extends BaseEntity {
    @ManyToOne
    private User author;
    private String title;
    private String content;

    @OneToMany(mappedBy = "post", fetch = LAZY, cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

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

    public Optional<PostComment> findCommentById(long id) {
        return comments
                .stream()
                .filter(comment -> comment.getId() == id)
                .findFirst();
    }

    public boolean deleteComment(PostComment postComment) {
        if(postComment == null) return false;

        return comments.remove(postComment);
    }

    public PostComment addComment(User author, String content) {
        PostComment postComment = new PostComment(author, this, content);
        comments.add(postComment);

        return postComment;
    }
}
