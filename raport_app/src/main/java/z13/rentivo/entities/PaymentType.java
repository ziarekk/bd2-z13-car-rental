package z13.rentivo.entities;


import javax.persistence.*;

import lombok.*;

@Data @Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "payment_types") // Inconsistency with model (payment_type)
public class PaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "type_id")
    private Long typeId;

    @NonNull
    private String name;
}