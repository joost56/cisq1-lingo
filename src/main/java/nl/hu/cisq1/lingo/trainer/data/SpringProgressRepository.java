package nl.hu.cisq1.lingo.trainer.data;

import nl.hu.cisq1.lingo.trainer.domain.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringProgressRepository extends JpaRepository<Progress, Long> {
}
