package vn.tg.ohana.repository.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "trends")
public class Trend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 45)
    private String name;

    @OneToOne
    @JoinColumn(name = "province_id")
    private Province province;

    @Column(name = "number_of_searches")
    private Long numberOfSearches;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Override
    public String toString() {
        return "Trend{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", province=" + province +
                ", numberOfSearches=" + numberOfSearches +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }
}
