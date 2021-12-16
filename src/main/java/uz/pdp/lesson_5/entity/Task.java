package uz.pdp.lesson_5.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity()
public class Task {
    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String name;

    @Column
    private String comment;

    @Column
    private Date lifeTime;

    @ManyToOne
    private User toWhome;

    @ManyToOne
    private User fromWhome;

    @Column
    private boolean success;
}
