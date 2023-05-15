package z13.rentivo.entities;


import javax.persistence.*;

import lombok.*;

@Data @Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "comment_id")
    private Long commentId;

    @NonNull
    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rental_id", referencedColumnName = "rental_id")
    private Rental rental;
}