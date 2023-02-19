package model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "rent_units")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class RentUnit implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String unitId;
    private String unitType;
    private String name;
    private String country;
    private String town;
    private String telephoneNumber;
    private String description;
    private Float rating;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="accountIdFK")
    private Account account;

    @OneToMany(   mappedBy = "rentUnit")
    private Set<Review> reviews;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imageId")
    private Image image;

    @OneToMany(mappedBy = "rentUnit")
    private Set<Room> rooms;


}
