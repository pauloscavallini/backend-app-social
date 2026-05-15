package dev.cavallini.social.services;

import dev.cavallini.social.domain.post.Post;
import dev.cavallini.social.infra.exceptions.InvalidPostException;
import dev.cavallini.social.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Object createPost(String authorId, String content) throws InvalidPostException {

        if (content.isBlank() || content.length() > 100) {
            throw new InvalidPostException("Conteudo do post deve possuir entre 1 e 100 caracteres");
        }

        var post = new Post(authorId, content);
        return postRepository.save(post);
    }

}
