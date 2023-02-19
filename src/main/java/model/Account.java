package model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Account implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String accountId;
    private String type;
    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userIdFK")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="loyaltyPointsFK")
    private LoyaltyPoints loyaltyPoints;

    @OneToMany(mappedBy = "account")
    private Set<Review> reviews;

    @OneToMany(mappedBy = "account")
    private Set<Notification> notifications;

    @OneToMany(mappedBy = "account")
    private Set<Message> messages;

    @OneToMany(mappedBy = "account")
    private Set<Chat> chats;

    @OneToMany(mappedBy = "account")
    private Set<Reservation> reservations;

    @OneToMany(mappedBy = "account")
    private Set<UpdateRequest> updateRequests;


}
