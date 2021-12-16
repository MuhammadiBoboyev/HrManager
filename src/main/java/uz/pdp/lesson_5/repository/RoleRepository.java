package uz.pdp.lesson_5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson_5.entity.Role;
import uz.pdp.lesson_5.entity.enums.RoleName;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(RoleName roleName);

    Set<Role> findAllByRoleName(RoleName roleName);
}
