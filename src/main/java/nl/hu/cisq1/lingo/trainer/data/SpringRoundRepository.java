package nl.hu.cisq1.lingo.trainer.data;

import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringRoundRepository extends JpaRepository<Round, Long> {
    Optional<Round> findById(Long id);
}
