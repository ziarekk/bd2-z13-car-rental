package z13.rentivo.entities;


import javax.persistence.*;

import org.hibernate.annotations.Check;

import lombok.*;

@Data @Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "user_roles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "role_id")
    private Long roleId;
    
    @NonNull
    @Check(constraints = "name in ('normal', 'employee', 'vip', 'superuser')")
    private String name;
}