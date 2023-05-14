package z13.rentivo.entities;


import javax.persistence.*;

import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "payment_id")
    private Long paymentId;

    @NonNull
    @Column(length = 30)
    private String status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bill_id", referencedColumnName = "bill_id")
    private Bill bill;

    @ManyToOne(optional = false)
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    private PaymentType paymentType;
}