package z13.rentivo.entities;


import javax.persistence.*;

import java.util.Date;

import lombok.*;

@Data @Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "location_history")
public class LocationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "location_id")
    private Long locationId;

    @NonNull
    private Float longitude;

    @NonNull
    private Float latitude;

    @NonNull
    @Column(name = "registration_timestamp")
    private Date registrationTimestamp;
}