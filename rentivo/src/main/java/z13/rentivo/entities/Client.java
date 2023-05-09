package z13.rentivo.entities;


import javax.persistence.*;

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
    private Date birthdate;

    @NonNull
    private String gender;

    @NonNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @NonNull
    @Column(name = "is_verified")
    private Boolean isVerified;
}