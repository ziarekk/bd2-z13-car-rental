package z13.rentivo.entities;


import javax.persistence.*;

import lombok.*;

@Data @Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "user_id")
    private Long userId;

    @NonNull
    private String login;

    @NonNull
    @Column(name = "hashed_password")
    private String hashedPassword;

    @NonNull
    private String email;

//    @OneToOne(optional = true, mappedBy = "user")
//    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private UserRole userRole;
}