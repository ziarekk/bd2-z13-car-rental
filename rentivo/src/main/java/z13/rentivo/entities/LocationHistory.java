package z13.rentivo.entities;


import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "location_history")
public class LocationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "location_id")
    private Long locationId;

    @NonNull
    @Column(precision = 7, scale = 5)
    private BigDecimal longitude;

    @NonNull
    @Column(precision = 7, scale = 5)
    private BigDecimal latitude;

    @NonNull
    @Column(name = "registration_timestamp")
    private Date registrationTimestamp;

    @ManyToOne(optional = false)
    @JoinColumn(name = "car_id", referencedColumnName = "car_id")
    private Car car;
}