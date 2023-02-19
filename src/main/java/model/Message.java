package model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Message implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String messageId;
    private String text;

    @ManyToOne
    @JoinColumn(name = "accountIdFK")
       private Account account;

//    @ManyToOne
//    @JoinColumn(name = "clientIdFK")
//    private Account client;

    @ManyToOne
    @JoinColumn(name = "chatIdFK")
    private Chat chat;


}
