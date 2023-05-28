package z13.rentivo.entities;


import javax.persistence.*;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Check;

import lombok.*;

@Data @Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "car_id")
    private Long carId;

    @NonNull
    @Min(value = 0)
    private Integer mileage;

    @NonNull
    @Column(name = "registration_number")
    private String registrationNumber;

    @NonNull
    @Column(name = "production_year")
    private Integer productionYear;

    @NonNull
    private Float longitude;

    @NonNull
    private Float latitude;

    @NonNull
    @Min(value = 0)
    @Column(name = "fuel_level")
    private Float fuelLevel;

    @NonNull
    @Column(name = "is_available_for_rent")
    private Boolean isAvailableForRent;

    @NonNull
    @Check(constraints = "fuel_type in ('benzyna', 'diesel', 'LPG', 'hybryda', 'elektryczny')")
    @Column(name = "fuel_type")
    private String fuelType;

    @NonNull
    @Min(value = 0)
    @Column(name = "fuel_capacity")
    private Float fuelCapacity;

    @NonNull
    private String model;

    @NonNull
    private String brand;

    @NonNull
    @Min(value = 1)
    private Integer seats;

    @NonNull
    @Check(constraints = "transmission IN ('manual', 'automatic')")
    private String transmission;

    @ManyToOne(optional = false)
    @JoinColumn(name = "segment_id", referencedColumnName = "segment_id")
    private Segment segment;
}