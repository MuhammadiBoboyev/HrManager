package uz.pdp.lesson_5.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.pdp.lesson_5.entity.enums.RoleName;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Salary {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User user;

    @Column
    private Timestamp time;

    public Salary(User user, Timestamp time) {
        this.user = user;
        this.time = time;
    }
}
