package z13.rentivo.entities;


import javax.persistence.*;

// import java.util.Date;

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
    @Column(name = "fuel_level")
    private Float fuelLevel;

    @NonNull
    @Column(name = "is_available_for_rent")
    private Boolean isAvailableForRent;

    @NonNull
    @Column(name = "fuel_type")
    private String fuelType;

    @NonNull
    @Column(name = "fuel_capacity")
    private Float fuelCapacity;

    @NonNull
    private String model;

    @NonNull
    private String brand;

    @NonNull
    private Integer seats;

    @NonNull
    private String transmission;

    @ManyToOne(optional = false)
    @JoinColumn(name = "segment_id", referencedColumnName = "segment_id")
    private Segment segment;
}