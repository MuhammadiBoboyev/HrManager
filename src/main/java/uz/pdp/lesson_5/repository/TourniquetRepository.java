package uz.pdp.lesson_5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.lesson_5.entity.Tourniquet;
import uz.pdp.lesson_5.projection.CustomTourniquet;

import java.util.UUID;

@RepositoryRestResource(path = "tourniquet", collectionResourceRel = "list", excerptProjection = CustomTourniquet.class)
public interface TourniquetRepository extends JpaRepository<Tourniquet, UUID> {
}
