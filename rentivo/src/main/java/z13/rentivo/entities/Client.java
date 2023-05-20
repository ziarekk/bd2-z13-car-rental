package z13.rentivo.entities;


import javax.persistence.*;

import org.hibernate.annotations.Check;

import java.util.Date;

import lombok.*;

@Data @Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "client_id")
    private Long clientId;

    @NonNull
    private String name;

    @NonNull
    private String surname;

    @NonNull
    @Column(name = "birth_date")
    private Date birthDate;

    @NonNull
    @Check(constraints = "gender in ('K', 'M', 'O')")
    private String gender;

    @NonNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @NonNull
    @Column(name = "is_verified")
    private Boolean isVerified;

    @OneToOne(optional = true, mappedBy = "client")
    private DriverLicence driverLicence;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}