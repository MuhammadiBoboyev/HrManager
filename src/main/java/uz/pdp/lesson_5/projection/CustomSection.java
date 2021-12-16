package uz.pdp.lesson_5.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.lesson_5.entity.Company;
import uz.pdp.lesson_5.entity.Section;

import java.util.UUID;

@Projection(types = Section.class)
public interface CustomSection {

    UUID getId();

    String getName();

    String getLeaderName();

    Company getCompany();
}
