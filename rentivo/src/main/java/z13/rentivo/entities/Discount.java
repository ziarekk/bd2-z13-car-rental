package z13.rentivo.entities;


import javax.persistence.*;

// import java.util.Date;

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
    private Float percent;

    @NonNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "rental_id", referencedColumnName = "rental_id")
    private Rental rental;    
}