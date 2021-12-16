package uz.pdp.lesson_5.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.lesson_5.entity.Company;

import java.util.UUID;

@Projection(types = Company.class)
public interface CustomCompany {

    UUID getId();

    String getName();

    String getDirectorName();
}
