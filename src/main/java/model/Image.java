package model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Image implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String imageId;
    private String imageName;

}
