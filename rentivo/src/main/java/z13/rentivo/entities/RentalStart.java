package z13.rentivo.entities;


import javax.persistence.*;

import java.util.Date;

import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rental_start")
public class RentalStart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "start_id")
    private Long startId;

    @NonNull
    @Column(name = "start_time")
    private Date startTime;

    @NonNull
    @Column(name = "start_mileage")
    private Integer startMileage;

    @OneToOne(optional = false, mappedBy = "rentalStart")
    private Rental rental;
}