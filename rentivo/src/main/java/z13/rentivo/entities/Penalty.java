package z13.rentivo.entities;


import javax.persistence.*;
import javax.validation.constraints.Min;

import lombok.*;

@Data @Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "penalties") //inconsistency with model (penalty)
public class Penalty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "penalty_id")
    private Long penaltyId;

    @NonNull
    @Min(value = 0)
    private Float amount;

    @NonNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "rental_id", referencedColumnName = "rental_id")
    private Rental rental;
}