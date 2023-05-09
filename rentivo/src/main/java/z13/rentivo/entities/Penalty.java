package z13.rentivo.entities;


import javax.persistence.*;

import java.util.Date;

import lombok.*;

@Data @Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "penalties") //inconsistency with model (penalty)
public class LocationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "penalty_id")
    private Long penaltyId;

    @NonNull
    private Float amount;

    @NonNull
    private String description;
}