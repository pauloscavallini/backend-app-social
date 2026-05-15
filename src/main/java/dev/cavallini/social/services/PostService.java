package dev.cavallini.social.services;

import dev.cavallini.social.domain.post.Post;
import dev.cavallini.social.domain.post.PostResponseDTO;
import dev.cavallini.social.infra.exceptions.InvalidPostException;
import dev.cavallini.social.infra.exceptions.OperationNotAllowedException;
import dev.cavallini.social.infra.exceptions.PostNotFoundException;
import dev.cavallini.social.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void createPost(String authorId, String content) throws InvalidPostException {

        if (content.isBlank() || content.length() > 100) {
            throw new InvalidPostException("Conteudo do post deve possuir entre 1 e 100 caracteres");
        }

        var post = new Post(authorId, content);
        postRepository.save(post);
    }

    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAllWithAuthorName();
    }

    public PostResponseDTO getPostById(String id) throws PostNotFoundException {
        var post = postRepository.findPostWithAuthorById(id);
        if (post == null) {
            throw new PostNotFoundException("Not Found");
        }
        return post;
    }

    public List<PostResponseDTO> getUserPosts(String id) {
        return postRepository.findPostsByAuthorId(id);
    }

    public void deleteById(String authorId, String id) throws PostNotFoundException, OperationNotAllowedException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Not Found"));

        if (!authorId.equals(post.getAuthor_id())) {
            throw new OperationNotAllowedException("Nao pode excluir post alheio");
        }

        postRepository.deleteById(id);
    }

}
