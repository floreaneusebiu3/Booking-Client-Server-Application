package model;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "loyalty_points")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter


public class LoyaltyPoints implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String loyaltyPointsId;
    private int value = 0;

}
