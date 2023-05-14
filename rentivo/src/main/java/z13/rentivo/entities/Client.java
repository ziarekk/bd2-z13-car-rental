package z13.rentivo.entities;


import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.Date;

import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "client_id")
    private Long clientId;

    @NonNull
    @Column(length = 30)
    private String name;

    @NonNull
    @Column(length = 30)
    private String surname;

    @NonNull
    @Column(name = "birth_date")
    private Date birthDate;

    @NonNull
    private Character gender;

    @NonNull
    @Column(name = "phone_number", length = 9)
    private String phoneNumber;

    @NonNull
    @Column(name = "is_verified")
    private Boolean isVerified;

    @OneToOne(optional = false)
    @JoinColumn(name = "licence_id", referencedColumnName = "licence_id")
    private DriverLicence driverLicence;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}