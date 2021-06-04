package nl.hu.cisq1.lingo.trainer.application;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.data.SpringRoundRepository;
import nl.hu.cisq1.lingo.trainer.domain.*;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameIdNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundIdNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundnotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
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
    private final SpringGameRepository gameRepository;
    private final SpringRoundRepository roundRepository;
    private final SpringWordRepository wordRepository;

    public TrainerService(WordService wordService, SpringGameRepository gameRepository, SpringRoundRepository roundRepository, SpringWordRepository wordRepository
    ) {
        this.wordService = wordService;
        this.gameRepository = gameRepository;
        this.roundRepository = roundRepository;
        this.wordRepository = wordRepository;
    }

    public Progress startNewGame() {
        String word = wordService.provideRandomWord(5);
        Game game = new Game(word);
        gameRepository.save(game);
        return new Progress(game);
    }

    public Progress startNewRound(Long gameId) {
        Game game = this.gameRepository.findById(gameId).orElseThrow(() -> new GameIdNotFoundException(gameId));
        Integer wordLength = game.getNextWordLength();
        game.startNewRound(wordService.provideRandomWord(wordLength));
        this.gameRepository.save(game);
        return new Progress(game);
    }

    public Progress guess(String attempt, Long roundId, Long gameId) {
        Round round = this.roundRepository.findById(roundId).orElseThrow(() -> new RoundIdNotFoundException(roundId));
        Game game = this.gameRepository.findById(gameId).orElseThrow(() -> new GameIdNotFoundException(gameId));
        List<Word> words = wordRepository.findAll();
        if (!wordExistsCheck(game, round, words, attempt)) {
            return new Progress(game);
        } else if (!game.getGameStatus().equals(GameStatus.ELIMINATED.toString()) && wordExistsCheck(game, round, words, attempt)) {
            game.guess(attempt, round);
            saveRoundAndGame(game, round);
            return new Progress(game);
        }
        return new Progress(game);
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

    public void saveRoundAndGame (Game game, Round round){
        this.gameRepository.save(game);
        this.roundRepository.save(round);
    }

    public Boolean wordExistsCheck(Game game, Round round, List<Word> words, String attempt){
        if (!game.wordExists(words, attempt, round)) {
            saveRoundAndGame(game, round);
            return false;
        } else {
            saveRoundAndGame(game, round);
            return true;
        }
    }
}