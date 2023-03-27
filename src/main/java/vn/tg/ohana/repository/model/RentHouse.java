package vn.tg.ohana.repository.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rent_house")
public class RentHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "price", precision = 14)
    private BigDecimal price;

    @Column(name = "capacity")
    private Long capacity;

    @Column(name = "area")
    private Long area;

    @OneToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @Column(name = "status")
    private boolean status;

}