package nl.hu.cisq1.lingo.trainer.data;

import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SpringGameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findById(Long id);
    @Query(nativeQuery = true, value = "SELECT game_status FROM game where id = ?1")
    Optional<String> findGameStatus(Long gameId);

}
