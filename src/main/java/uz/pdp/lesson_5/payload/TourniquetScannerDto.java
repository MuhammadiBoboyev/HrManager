package uz.pdp.lesson_5.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import uz.pdp.lesson_5.entity.Tourniquet;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TourniquetScannerDto {
    @NotNull
    private UUID tourniquetId;
    @NotNull
    private Timestamp time;
    @NotNull
    private boolean isInOut;
}
