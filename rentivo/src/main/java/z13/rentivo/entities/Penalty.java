package z13.rentivo.entities;


import javax.persistence.*;

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
    private Float amount;

    @NonNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "rental_id", referencedColumnName = "rental_id")
    private Rental rental;
}