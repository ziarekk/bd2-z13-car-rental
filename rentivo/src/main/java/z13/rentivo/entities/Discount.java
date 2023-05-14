package z13.rentivo.entities;


import javax.persistence.*;

import lombok.*;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discounts") //inconsistency with model (discount)
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "discount_id")
    private Long discountId;

    @NonNull
    @Column(precision = 5, scale = 2)
    private BigDecimal percent;

    @NonNull
    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "rental_id", referencedColumnName = "rental_id")
    private Rental rental;
}