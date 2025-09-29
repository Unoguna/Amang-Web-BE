package back.domain.post.post.controller;

import back.domain.post.post.dto.PostDto;
import back.domain.post.post.dto.PostModifyReqBody;
import back.domain.post.post.dto.PostWithAuthorDto;
import back.domain.post.post.dto.PostWriteReqBody;
import back.domain.post.post.entity.Post;
import back.domain.post.post.service.PostService;
import back.domain.user.user.entity.User;
import back.global.Rq.Rq;
import back.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name="PostController", description = "API 글 컨트롤러")
@SecurityRequirement(name = "bearerAuth")
public class PostController {
    private final PostService postService;
    private final Rq rq;

    @Transactional(readOnly = true)
    @GetMapping
    @Operation(summary = "다건 조회")
    public List<PostWithAuthorDto> getItems(){

        List<Post> items = postService.getList();

        return items
                .stream()
                .map(PostWithAuthorDto::new)
                .toList();
    }

    @Transactional
    @DeleteMapping("/{id}")
    @Operation(summary = "삭제")
    public RsData<PostDto> delete(
            @PathVariable Long id
    ){
        User actor = rq.getActor();

        Post post = postService.findById(id);

        post.checkActorCanDelete(actor);

        postService.delete(post);

        return new RsData<>("200-1", "%d번 게시글이 삭제되었습니다.".formatted(id), new PostDto(post));
    }

    @PostMapping
    @Transactional
    @Operation(summary = "작성")
    public RsData<PostWithAuthorDto> write(
            @Valid @RequestBody PostWriteReqBody reqBody
    ){
        User actor = rq.getActor();
        Post post = postService.create(actor, reqBody.title(), reqBody.content());

        return new RsData<>(
                "201-1",
                "%d번 게시글이 생성되었습니다.".formatted(post.getId()),
                new PostWithAuthorDto(post)
        );
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "수정")
    public RsData<Void> modify(
            @PathVariable long id,
            @Valid @RequestBody PostModifyReqBody reqBody
    ){
        User actor = rq.getActor();

        Post post = postService.findById(id);
        postService.update(post, reqBody.title(), reqBody.content());

        post.checkActorCanModify(actor);

        return new RsData<>(
                "200-1",
                "%d번 게시글이 수정되었습니다.".formatted(id)
        );
    }
}
