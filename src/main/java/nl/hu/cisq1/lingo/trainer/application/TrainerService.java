package nl.hu.cisq1.lingo.trainer.application;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.data.SpringRoundRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameIdNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundIdNotFoundException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TrainerService {
    private WordService wordService;
    private SpringGameRepository gameRepository;
    private SpringRoundRepository roundRepository;

    public TrainerService(WordService wordService, SpringGameRepository gameRepository, SpringRoundRepository roundRepository) {
        this.wordService = wordService;
        this.gameRepository = gameRepository;
        this.roundRepository = roundRepository;
    }

    public Game startNewGame() {
        Game game = new Game();
        game.startNewGame();
        this.gameRepository.save(game);
        return game;
    }

    public Game getGameById(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new GameIdNotFoundException(id));;
        return game;
    }

    public String startNewRound(Long gameId){
        Round round = new Round(wordService.provideRandomWord(5));
        Game game = this.gameRepository.findById(gameId).orElseThrow(() -> new GameIdNotFoundException(gameId));
        game.setRound(round);
        String show = game.startNewRound(round.getWordToGuess());
        round.setGame(game);
        round.setPreviousHint(show);
        this.gameRepository.save(game);
        return show;
    }

    public String guess (String attempt, Long roundId){
        Round round = this.roundRepository.findById(roundId).orElseThrow(() -> new RoundIdNotFoundException(roundId));
        Long gameId = round.getGame().getId();
        Game game = this.gameRepository.findById(gameId).orElseThrow(() -> new GameIdNotFoundException(gameId));
        String show = game.guess(attempt, round);
        this.gameRepository.save(game);
        this.roundRepository.save(round);
        return show;
    }

}
