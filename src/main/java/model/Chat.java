package model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "chats")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Chat implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String chatId;

    @ManyToOne
    @JoinColumn(name = "accountIdFK")
    private Account account;

    @OneToMany(mappedBy = "chat")
    private Set<Message> messages;


}
