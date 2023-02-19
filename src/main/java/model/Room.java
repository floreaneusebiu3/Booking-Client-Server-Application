package model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "rooms")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Room implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String roomId;
    private int capacity;
    private float price;
    private String Facilitati;

    @ManyToOne()
    @JoinColumn(name = "rentUnitIdFK")
    private RentUnit rentUnit;

    @OneToMany(mappedBy = "room")
      private Set<Reservation> reservations;

}
