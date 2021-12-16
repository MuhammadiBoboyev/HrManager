package uz.pdp.lesson_5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson_5.entity.InputOutputTourniquetScanner;

import java.util.List;
import java.util.UUID;

public interface TourniquetScannerRepository extends JpaRepository<InputOutputTourniquetScanner, UUID> {
    List<InputOutputTourniquetScanner> findAllByTourniquetId(UUID tourniquet_id);
}
