package z13.rentivo.entities;


import javax.persistence.*;

import java.util.Date;

import lombok.*;

@Data @Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "rental_end")
public class RentalEnd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "end_id")
    private Long endId;

    @NonNull
    @Column(name = "end_time")
    private Date endTime;

    @NonNull
    @Column(name = "end_mileage")
    private Float endMileage;

    @OneToOne(mappedBy = "rental")
    @JoinColumn(name = "rental_id")
    private Rental rental;
}