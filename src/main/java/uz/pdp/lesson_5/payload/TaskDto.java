package uz.pdp.lesson_5.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private String name;
    @NotNull
    private String comment;
    @NotNull
    private Date lifeTime;
    @NotNull
    private UUID toWhomeId;
    @NotNull
    private UUID fromWhomeId;
    @NotNull
    private boolean success = false;
}
