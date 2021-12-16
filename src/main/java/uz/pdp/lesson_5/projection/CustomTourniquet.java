package uz.pdp.lesson_5.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.lesson_5.entity.Tourniquet;

import java.util.UUID;

@Projection(types = Tourniquet.class)
public interface CustomTourniquet {
    UUID getId();

    String getName();
}
