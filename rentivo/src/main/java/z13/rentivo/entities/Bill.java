package z13.rentivo.entities;


import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Date;

import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "bill_id")
    private Long billId;

    @NonNull
    @Column(columnDefinition = "money")
    private Float amount;

    @NonNull
    @Column(name = "date_due")
    private Date dateDue;

    @ManyToOne(optional = true)
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    private Payment payment;
}