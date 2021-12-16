package uz.pdp.lesson_5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.lesson_5.entity.Company;
import uz.pdp.lesson_5.entity.Salary;
import uz.pdp.lesson_5.projection.CustomCompany;

import java.util.List;
import java.util.UUID;

public interface SalaryRepository extends JpaRepository<Salary, UUID> {
    List<Salary> findAllByUserId(UUID user_id);
}
