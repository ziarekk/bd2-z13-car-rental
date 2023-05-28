package z13.rentivo.entities;


import javax.persistence.*;
import javax.validation.constraints.Min;

import lombok.*;

@Data @Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "segments")
public class Segment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "segment_id")
    private Long segmentId;

    @NonNull
    private String name;

    @NonNull
    @Min(value = 0)
    @Column(name = "rental_fee")
    private Float rentalFee;

    @NonNull
    @Min(value = 0)
    @Column(name = "km_rate")
    private Float kmRate;

    @NonNull
    @Min(value = 0)
    @Column(name = "hour_rate")
    private Float hourRate;
}