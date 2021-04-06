package nl.hu.cisq1.lingo.trainer.application;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.data.SpringProgressRepository;
import nl.hu.cisq1.lingo.trainer.data.SpringRoundRepository;
import nl.hu.cisq1.lingo.trainer.domain.*;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameIdNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundIdNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundnotFoundException;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private SpringProgressRepository progressRepository;

    public TrainerService(WordService wordService, SpringGameRepository gameRepository, SpringRoundRepository roundRepository, SpringWordRepository wordRepository, SpringProgressRepository progressRepository
    ) {
        this.wordService = wordService;
        this.gameRepository = gameRepository;
        this.roundRepository = roundRepository;
        this.wordRepository = wordRepository;
        this.progressRepository = progressRepository;
    }

    public Progress startNewGame() {
        Game game = new Game();
        Progress progress = game.startNewGame();
        this.gameRepository.save(game);
        startNewRound(game.getId());
        return progress;
    }

    public Game getGameById(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new GameIdNotFoundException(id));
        return game;
    }

    public Round getRoundById(Long id) {
        Round round = roundRepository.findById(id).orElseThrow(() -> new RoundIdNotFoundException(id));
        return round;
    }

    public List<Round> getRoundsByGameId(Long id) {
        List<Round> rounds = roundRepository.countRoundsByGameId(id).orElseThrow(() -> new RoundnotFoundException());
        return rounds;
    }

    public Progress startNewRound(Long gameId) {
        Game game = this.gameRepository.findById(gameId).orElseThrow(() -> new GameIdNotFoundException(gameId));
        Integer wordLength = game.getNextWordLength();
        if (game.getProgress().getMessage().equals(GameStatus.ELIMINATED.toString())) {
            return game.getProgress();
        } else {
            Round round = new Round(wordService.provideRandomWord(wordLength));
            game.setRound(round);
            Progress progress = game.startNewRound(round.getWordToGuess());
            round.setGame(game);
            round.setPreviousHint(progress.getHints());
            round.setRoundStatus(RoundStatus.IN_PROGRESS.toString());
            this.gameRepository.save(game);
//            this.progressRepository.save(progress);
            return progress;
        }
    }

    public Progress guess(String attempt, Long roundId, Long gameId) {
        Round round = this.roundRepository.findById(roundId).orElseThrow(() -> new RoundIdNotFoundException(roundId));
        Game game = this.gameRepository.findById(gameId).orElseThrow(() -> new GameIdNotFoundException(gameId));
        List<Word> words = wordRepository.findAll();
        Progress progress = game.getProgress();
        if (!words.toString().contains(attempt)) {
            game.setGameStatus(GameStatus.ELIMINATED.toString());
            progress.setMessage(GameStatus.ELIMINATED.toString());
            round.setRoundStatus(RoundStatus.FAILED.toString() + "This word does not exist!");

            this.gameRepository.save(game);
            this.roundRepository.save(round);
            return progress;
        }
        else if (game.getGameStatus().equals(GameStatus.ELIMINATED.toString())) {
            progress.setMessage(GameStatus.ELIMINATED.toString());
            round.setRoundStatus(RoundStatus.FAILED.toString());

            this.gameRepository.save(game);
            this.roundRepository.save(round);
            return progress;
        } else if (!game.getGameStatus().equals(GameStatus.ELIMINATED.toString()) && words.toString().contains(attempt)) {
            progress.setMessage(GameStatus.PLAYING.toString());

            game.guess(attempt, round);
            this.gameRepository.save(game);
            this.roundRepository.save(round);
            return progress;
        } else {
            game.setGameStatus(GameStatus.ELIMINATED.toString());
            progress.setMessage(GameStatus.ELIMINATED.toString());
            round.setRoundStatus(RoundStatus.FAILED.toString());

            this.gameRepository.save(game);
            this.roundRepository.save(round);
            return progress;
        }


    }
}
