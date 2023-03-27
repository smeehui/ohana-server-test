package vn.tg.ohana.repository.model;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "post", indexes = {
        @Index(name = "fk_rent_house_idx", columnList = "rent_house_id"),
        @Index(name = "fk_user_idx", columnList = "user_id"),
        @Index(name = "fk_category_idx", columnList = "category_id")
})
@TypeDef(
        typeClass = JsonType.class,
        defaultForType = Location.class
)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rent_house_id")
    private RentHouse rentHouse;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "slug", length = 45)
    private String slug;

    @Column(name = "description_content")
    private String descriptionContent;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "location", nullable = false, columnDefinition = "JSON")
    private Location location;

    @Column(name = "thumbnail_id", length = 45)
    private String thumbnailId;

    @OneToMany(mappedBy = "post")
    private List<PostMedia> postMedia;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "utilities", nullable = false, columnDefinition = "JSON")
    private String utilities;

    @Column(name = "status", nullable = false)
    private StatusPost status;


}