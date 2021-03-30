package nl.hu.cisq1.lingo.trainer.data;

import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SpringRoundRepository extends JpaRepository<Round, Long> {
    Optional<Round> findById(Long id);
    @Query(nativeQuery = true, value = "SELECT word_to_guess FROM rounds where fk_game_id = ?1 ORDER BY id DESC LIMIT 1")
    Optional<String> findLastWord(Long gameId);
    @Query(nativeQuery = true, value = "SELECT fk_game_id FROM rounds where fk_game_id = ?1")
    Optional<List> countRoundsByGameId(Long gameId);

}
