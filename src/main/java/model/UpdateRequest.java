package model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "update_requests")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class UpdateRequest {
    @Id
    @Column(unique = true, nullable = false)
    private String requestId;
    private String cui;
    private Boolean accepted;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="rentUnitIdFK")
    private RentUnit rentUnit;

    @ManyToOne
    @JoinColumn(name = "accountIdFK")
    private Account account;

}
