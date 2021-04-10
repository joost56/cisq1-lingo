package nl.hu.cisq1.lingo.trainer.application;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.data.SpringRoundRepository;
import nl.hu.cisq1.lingo.trainer.domain.*;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameIdNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundIdNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundnotFoundException;
import nl.hu.cisq1.lingo.trainer.presentation.DTO.ProgressDTO;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TrainerService {
    private final WordService wordService;
    private SpringGameRepository gameRepository;
    private SpringRoundRepository roundRepository;
    private SpringWordRepository wordRepository;

    public TrainerService(WordService wordService, SpringGameRepository gameRepository, SpringRoundRepository roundRepository, SpringWordRepository wordRepository
    ) {
        this.wordService = wordService;
        this.gameRepository = gameRepository;
        this.roundRepository = roundRepository;
        this.wordRepository = wordRepository;
    }

    public ProgressDTO startNewGame() {
        String word = wordService.provideRandomWord(5);
        Game game = new Game(word);
        gameRepository.save(game);
        return new ProgressDTO(game);
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id).orElseThrow(() -> new GameIdNotFoundException(id));
    }

    public Round getRoundById(Long id) {
        return roundRepository.findById(id).orElseThrow(() -> new RoundIdNotFoundException(id));
    }

    public List<Round> getRoundsByGameId(Long id) {
        return (List<Round>) roundRepository.countRoundsByGameId(id).orElseThrow(() -> new RoundnotFoundException());
    }

    public ProgressDTO startNewRound(Long gameId) {
        Game game = this.gameRepository.findById(gameId).orElseThrow(() -> new GameIdNotFoundException(gameId));
        Integer wordLength = game.getNextWordLength();
        game.startNewRound(wordService.provideRandomWord(wordLength));
        this.gameRepository.save(game);
        return new ProgressDTO(game);
    }

    public ProgressDTO guess(String attempt, Long roundId, Long gameId) {
        Round round = this.roundRepository.findById(roundId).orElseThrow(() -> new RoundIdNotFoundException(roundId));
        Game game = this.gameRepository.findById(gameId).orElseThrow(() -> new GameIdNotFoundException(gameId));
        List<Word> words = wordRepository.findAll();
        if (!words.toString().contains(attempt)) {
            game.setGameStatus(GameStatus.ELIMINATED.toString());
            round.setRoundStatus(RoundStatus.FAILEDBYNONEXISTINGWORD.toString());
            this.gameRepository.save(game);
            this.roundRepository.save(round);
            return new ProgressDTO(game);
        } else if (!game.getGameStatus().equals(GameStatus.ELIMINATED.toString()) && words.toString().contains(attempt)) {
            game.guess(attempt, round);
            this.gameRepository.save(game);
            this.roundRepository.save(round);
            return new ProgressDTO(game);
        } else {
            this.gameRepository.save(game);
            this.roundRepository.save(round);
            return new ProgressDTO(game);
        }
    }
}