package dev.cavallini.social.controller;

import dev.cavallini.social.domain.post.FormattedPostResponseDTO;
import dev.cavallini.social.domain.post.Post;
import dev.cavallini.social.domain.post.PostRequestDTO;
import dev.cavallini.social.domain.post.PostResponseDTO;
import dev.cavallini.social.domain.user.User;
import dev.cavallini.social.repositories.PostRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {

    private final PostRepository repository;

    @GetMapping("/all")
    public ResponseEntity<List<PostResponseDTO>> getPosts() {
    List<PostResponseDTO> posts = repository.findAllWithAuthorName();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PostResponseDTO>> getUserPosts(@PathVariable @Valid String userId) {
        List<PostResponseDTO> postResponseDTOS = repository.findPostsByAuthorId(userId);
        return ResponseEntity.ok(postResponseDTOS);
    }

    @PostMapping("/create")
    public ResponseEntity<FormattedPostResponseDTO> createPost(@RequestBody PostRequestDTO data) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();
        String authorId = user.getId();

        Post newPost = new Post(authorId, data.content());
        repository.save(newPost);

        return ResponseEntity.status(201).body(new FormattedPostResponseDTO(
                "Post created successfully",
                new PostResponseDTO(newPost, user.getUsername())));
    }
}
