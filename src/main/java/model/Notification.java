package model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "notifications")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Notification implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String notificationnId;
    private String information;
    private String status;

    @ManyToOne
    @JoinColumn(name = "accountIdFK")
    private Account account;


}
