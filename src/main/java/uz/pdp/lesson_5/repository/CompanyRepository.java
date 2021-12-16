package uz.pdp.lesson_5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.lesson_5.entity.Company;
import uz.pdp.lesson_5.projection.CustomCompany;

import java.util.UUID;

@RepositoryRestResource(path = "company", collectionResourceRel = "list", excerptProjection = CustomCompany.class)
public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
