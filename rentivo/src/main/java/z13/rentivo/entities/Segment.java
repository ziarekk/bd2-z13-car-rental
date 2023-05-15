package z13.rentivo.entities;


import javax.persistence.*;

import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "segments")
public class Segment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "segment_id")
    private Long segmentId;

    @NonNull
    @Column(length = 10)
    private String name;

    @NonNull
    @Column(name = "rental_fee")
    private Integer rentalFee;

    @NonNull
    @Column(name = "km_rate")
    private Integer kmRate;

    @NonNull
    @Column(name = "hour_rate")
    private Integer hourRate;
}