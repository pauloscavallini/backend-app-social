package dev.cavallini.social.domain.post;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@Entity(name = "posts")
@Table(name = "posts")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "author_id")
    private String author_id;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private Date created_at;

    public Post(String author_id, String content) {
        this.author_id = author_id;
        this.content = content;
        this.created_at = Date.from(Instant.now());
    }
}
