package uz.pdp.lesson_5.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class InputOutputTourniquetScanner {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Tourniquet tourniquet;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp time;

    @Column(nullable = false)
    private boolean isInOut;
}
