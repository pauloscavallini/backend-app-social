package dev.cavallini.social.domain.post;

import java.util.Date;

public record PostResponseDTO(String id, String author_id, String content, Date created_at, String author_name) {
    public PostResponseDTO(Post post, String author_name) {
        this(post.getId(), post.getAuthor_id(), post.getContent(), post.getCreated_at(), author_name);
    }
}
