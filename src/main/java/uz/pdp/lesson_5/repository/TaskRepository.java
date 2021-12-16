package uz.pdp.lesson_5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson_5.entity.Task;
import uz.pdp.lesson_5.entity.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findAllByToWhomeId(UUID toWhome_id);

    List<Task> findByToWhomeAndSuccess(User toWhome, boolean success);
}
