package model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class User implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String userId;
    private String firstName;
    private String lastName;
    private String mail;
    private int age;

    public void createUser(String firstName, String lastName, String mail, String age) {
       this.firstName = firstName;
       this.lastName = lastName;
       this.mail = mail;
       try {
           this.age = Integer.parseInt(age);
       } catch (Exception e) {
           System.out.println("age must be a number");
       }

    }
}
