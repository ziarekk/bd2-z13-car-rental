package z13.rentivo.entities;


import javax.persistence.*;

import java.util.Date;

import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "driver_licences")
public class DriverLicence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "licence_id")
    private Long licenceId;

    @NonNull
    @Column(length = 20)
    private String number;

    @NonNull
    @Column(name = "date_obtained")
    private Date dateObtained;

    @NonNull
    @Column(name = "expiration_date")
    private Date expirationDate;

    @NonNull
    @Column(length = 5)
    private String category;

    @OneToOne(optional = false)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;
}
