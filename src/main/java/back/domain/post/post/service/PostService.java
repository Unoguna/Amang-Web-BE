package back.domain.post.post.service;

import back.domain.post.post.entity.Post;
import back.domain.post.post.repository.PostRepository;
import back.domain.post.postComment.entity.PostComment;
import back.domain.user.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public List<Post> getList() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        return postRepository.findById(id).get();
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public Post create(User author, String title, String content) {
        Post post = new Post(author, title, content);

        return postRepository.save(post);
    }

    public void update(Post post, String title, String content) {
        post.modify(title, content);
    }

    public void deleteComment(Post post, PostComment postComment) {
        // 여기서 deleteComment는 단순히 post.getComments().remove(postComment) 호출
        // 하지만 Post.comments 매핑에 orphanRemoval=true가 걸려있기 때문에
        // 컬렉션에서 제거된 PostComment는 "고아 객체"로 간주 → flush/commit 시 DELETE SQL 실행
        // 즉, 별도로 postCommentRepository.delete(...)를 호출하지 않아도 DB에서 삭제됨
        post.deleteComment(postComment);
    }

    public void modifyComment(PostComment postComment, String content) {
        postComment.modify(content);
    }

    public PostComment writeComment(User author, Post post, String content) {
        return post.addComment(author, content);
    }

    public void flush() {
        postRepository.flush();
    }
}
