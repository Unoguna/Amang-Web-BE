package back.domain.post.post.service;

import back.domain.post.post.entity.Post;
import back.domain.post.post.repository.PostRepository;
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
}
