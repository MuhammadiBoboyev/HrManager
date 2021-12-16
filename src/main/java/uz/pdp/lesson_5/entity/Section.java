package uz.pdp.lesson_5.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity()
public class Section {
    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String name;

    @Column
    private String leaderName;

    @ManyToOne
    private Company company;
}
