package nl.hu.cisq1.lingo.trainer.application;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.data.SpringRoundRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameIdNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundIdNotFoundException;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class TrainerService {
    private WordService wordService;
    private SpringGameRepository gameRepository;
    private SpringRoundRepository roundRepository;
    private SpringWordRepository wordRepository;

    public TrainerService(WordService wordService, SpringGameRepository gameRepository, SpringRoundRepository roundRepository, SpringWordRepository wordRepository) {
        this.wordService = wordService;
        this.gameRepository = gameRepository;
        this.roundRepository = roundRepository;
        this.wordRepository = wordRepository;
    }

    public Game startNewGame() {
        Game game = new Game();
        game.startNewGame();
        this.gameRepository.save(game);
        return game;
    }

    public Game getGameById(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new GameIdNotFoundException(id));
        return game;
    }

    public Round getRoundById(Long id) {
        Round round = roundRepository.findById(id).orElseThrow(() -> new RoundIdNotFoundException(id));
        return round;
    }

    public Progress startNewRound(Long gameId) {
        Game game = this.gameRepository.findById(gameId).orElseThrow(() -> new GameIdNotFoundException(gameId));
        Optional<String> lastWord = roundRepository.findLastWord(gameId);
        int wordLength = 5;
        try {
            if (lastWord.get().length() == 5) {
                wordLength = 6;
            } else if (lastWord.get().length() == 6) {
                wordLength = 7;
            } else if (lastWord.get().length() == 7) {
                wordLength = 5;
            }
        } catch (NoSuchElementException e) {
            wordLength = 5;
        }
        if (game.getGameStatus() == GameStatus.ELIMINATED.toString()) {
            return game.getProgress();
        } else {
            Round round = new Round(wordService.provideRandomWord(wordLength));
            game.setRound(round);
            Progress show = game.startNewRound(round.getWordToGuess());
            round.setGame(game);
            round.setPreviousHint(show.getHints());
            this.gameRepository.save(game);
            return show;
        }
    }

    public Progress guess(String attempt, Long roundId) {
        Round round = this.roundRepository.findById(roundId).orElseThrow(() -> new RoundIdNotFoundException(roundId));
        Long gameId = round.getGame().getId();
        Game game = this.gameRepository.findById(gameId).orElseThrow(() -> new GameIdNotFoundException(gameId));
        List<Word> words = wordRepository.findAll();
        if (game.getGameStatus() == GameStatus.ELIMINATED.toString()) {
            this.gameRepository.save(game);
            this.roundRepository.save(round);
            Progress progress = game.getProgress();
            progress.setMessage("You have been eliminated, start a new game");
            return progress;
        } else if (!game.getGameStatus().equals(GameStatus.ELIMINATED.toString()) && words.toString().contains(attempt)) {
            this.gameRepository.save(game);
            this.roundRepository.save(round);
            game.guess(attempt, round);
            Progress progress = game.getProgress();
            progress.setMessage("Guess again");
            return progress;
        } else {
            game.setGameStatus(GameStatus.ELIMINATED.toString());
            this.gameRepository.save(game);
            this.roundRepository.save(round);
            Progress progress = game.getProgress();
            progress.setMessage("This word does not exist in this trainer");
            return progress;
        }


    }
}
