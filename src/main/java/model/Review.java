package model;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name ="reviews")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Review implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String reviewId;
    private String text;
    private Float grade;

    @ManyToOne
    @JoinColumn(name = "rentUnitIdFK")
    private RentUnit rentUnit;

    @ManyToOne
    @JoinColumn(name = "accountIdFK")
    private Account account;


}
