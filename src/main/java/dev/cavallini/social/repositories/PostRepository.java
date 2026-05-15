package dev.cavallini.social.repositories;

import dev.cavallini.social.domain.post.Post;
import dev.cavallini.social.domain.post.PostResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, String> {
    @Query("SELECT new dev.cavallini.social.domain.post.PostResponseDTO(p.id, p.author_id, p.content, p.created_at, u.login, u.displayname) " +
            "FROM posts p JOIN users u ON p.author_id = u.id " +
            "ORDER BY p.created_at DESC")
    List<PostResponseDTO> findAllWithAuthorName();

    @Query("SELECT new dev.cavallini.social.domain.post.PostResponseDTO(p.id, p.author_id, p.content, p.created_at, u.login, u.displayname) " +
            "FROM posts p JOIN users u ON p.author_id = u.id " +
            "WHERE p.author_id = :authorId")
    List<PostResponseDTO> findPostsByAuthorId(@Param("authorId") String authorId);

    @Query("SELECT new dev.cavallini.social.domain.post.PostResponseDTO(p.id, p.author_id, p.content, p.created_at, u.login, u.displayname) " +
            "FROM posts p JOIN users u ON p.author_id = u.id " +
            "WHERE p.id = :postId")
    PostResponseDTO findPostWithAuthorById(@Param("postId") String postId);
}
