package dev.cavallini.social.controller;

import dev.cavallini.social.domain.post.FormattedPostResponseDTO;
import dev.cavallini.social.domain.post.Post;
import dev.cavallini.social.domain.post.PostRequestDTO;
import dev.cavallini.social.domain.post.PostResponseDTO;
import dev.cavallini.social.domain.user.User;
import dev.cavallini.social.infra.dto.ApiSuccessDTO;
import dev.cavallini.social.repositories.PostRepository;
import dev.cavallini.social.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {

    private final PostRepository repository;

    private final PostService postService;


    @GetMapping("/all")
    public ResponseEntity<List<PostResponseDTO>> getPosts() {
    List<PostResponseDTO> posts = repository.findAllWithAuthorName();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable @Valid String postId) {
        PostResponseDTO post = repository.findPostWithAuthorById(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/author/{userId}")
    public ResponseEntity<List<PostResponseDTO>> getUserPosts(@PathVariable @Valid String userId) {
        List<PostResponseDTO> postResponseDTOS = repository.findPostsByAuthorId(userId);
        return ResponseEntity.ok(postResponseDTOS);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiSuccessDTO> createPost(@RequestBody @Valid PostRequestDTO data) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();
        String authorId = user.getId();

        postService.createPost(authorId, data.content());

        return ResponseEntity.status(201).body(new ApiSuccessDTO("Post criado com sucesso"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable @Valid String id) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();
        String authorId = user.getId();

        Post post = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        if (!authorId.equals(post.getAuthor_id())) {
            return ResponseEntity.status(403).body("Não pode excluir post alheio");
        }

        repository.deleteById(id);

        return ResponseEntity.ok("Post " + id + " deletado com sucesso por " + authorId);
    }
}
