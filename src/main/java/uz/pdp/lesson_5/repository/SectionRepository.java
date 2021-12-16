package uz.pdp.lesson_5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.lesson_5.entity.Section;
import uz.pdp.lesson_5.projection.CustomSection;

import java.util.UUID;

@RepositoryRestResource(path = "section", collectionResourceRel = "list", excerptProjection = CustomSection.class)
public interface SectionRepository extends JpaRepository<Section, UUID> {
}
