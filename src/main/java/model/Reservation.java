package model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "reservations")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Reservation implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String reservationId;
    private int checkInDay;
    private int checkInMonth;
    private int checkInYear;
    private int checkOutDay;
    private int checkOutMonth;
    private int checkOutYear;

    @ManyToOne
    @JoinColumn(name = "accountIdFK")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "roomIdFK")
    private Room room;

}
