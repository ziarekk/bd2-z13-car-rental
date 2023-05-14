package z13.rentivo.entities;


import javax.persistence.*;

import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "rental_id")
    private Long rentalId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "car_id", referencedColumnName = "car_id")
    private Car car;

    @OneToOne(optional = false)
    @JoinColumn(name = "start_id", referencedColumnName = "start_id")
    private RentalStart rentalStart;

    @OneToOne(optional = true)
    @JoinColumn(name = "end_id", referencedColumnName = "end_id")
    private RentalEnd rentalEnd;
}