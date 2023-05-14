package z13.rentivo.entities;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

// import java.util.Date;

import lombok.*;
import org.apache.qpid.proton.amqp.UnsignedInteger;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "car_id")
    private Long carId;

    @NonNull
    private Integer mileage;

    @NonNull
    @Column(name = "registration_number", length = 10)
    private String registrationNumber;

    @NonNull
    @Column(name = "production_year")
    private Integer productionYear;

    @NonNull
    @Column(precision = 7, scale = 5)
    private BigDecimal longitude;

    @NonNull
    @Column(precision = 7, scale = 5)
    private BigDecimal latitude;

    @NonNull
    @Column(name = "fuel_level", precision = 4, scale = 2)
    private BigDecimal fuelLevel;

    @NonNull
    @Column(name = "is_available_for_rent")
    private Boolean isAvailableForRent;

    @NonNull
    @Column(name = "fuel_type", length = 20)
    private String fuelType;

    @NonNull
    @Column(name = "fuel_capacity", precision = 4, scale = 2)
    private BigDecimal fuelCapacity;

    @NonNull
    @Column(length = 20)
    private String model;

    @NonNull
    @Column(length = 20)
    private String brand;

    @NonNull
    private Integer seats;

    @NonNull
    @Column(length = 20)
    private String transmission;

    @ManyToOne(optional = false)
    @JoinColumn(name = "segment_id", referencedColumnName = "segment_id")
    private Segment segment;
}