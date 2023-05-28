package z13.rentivo.entities;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.*;

@Data @Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "discounts") //inconsistency with model (discount)
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "discount_id")
    private Long discountId;

    @NonNull
    @Min(value = 0)
    @Max(value = 100)
    private Float percent;

    @NonNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "rental_id", referencedColumnName = "rental_id")
    private Rental rental;    
}