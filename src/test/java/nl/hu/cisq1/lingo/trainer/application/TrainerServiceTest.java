package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.data.SpringProgressRepository;
import nl.hu.cisq1.lingo.trainer.data.SpringRoundRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameIdNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundIdNotFoundException;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.exception.WordLengthNotSupportedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

class TrainerServiceTest {

//    @Test
//    @DisplayName("Start a new game")
//    void providesGame(){
//
//        WordService wordService = mock(WordService.class);
//        when(wordService.provideRandomWord(5)).thenReturn("baard");
//        SpringGameRepository repository = mock(SpringGameRepository.class);
//        SpringRoundRepository roundRepository = mock(SpringRoundRepository.class);
//        SpringWordRepository wordRepository = mock(SpringWordRepository.class);
//        SpringProgressRepository progressRepository = mock(SpringProgressRepository.class);
//
//        TrainerService trainerService = new TrainerService(wordService, repository, roundRepository, wordRepository, progressRepository);
//        Progress progress = trainerService.startNewGame();
//
//        assertEquals(null, progress.getHints());
//    }
//
//    @Test
//    @DisplayName("Make a guess")
//    void makesAGuess(){
//        WordService wordService = mock(WordService.class);
//        when(wordService.provideRandomWord(6)).thenReturn("hoeden");
//
//        Game game = new Game();
//        game.startNewGame();
//        game.startNewRound("woord");
//        game.guess("waren", game.getRounds().get(0));
//
//        SpringGameRepository repository = mock(SpringGameRepository.class);
//        SpringRoundRepository roundRepository = mock(SpringRoundRepository.class);
//        SpringWordRepository wordRepository = mock(SpringWordRepository.class);
//        SpringProgressRepository progressRepository = mock(SpringProgressRepository.class);
//        when(roundRepository.findById(anyLong())).thenReturn(Optional.of(game.getRounds().get(0)));
//
//        TrainerService trainerService = new TrainerService(wordService, repository, roundRepository, wordRepository, progressRepository);
//        Progress progress = trainerService.guess("woord", 0L);
//
//        assertEquals(null, progress.getHints());
//    }
//
//
//
//    @Test
//    @DisplayName("Start a new round")
//    void providesRound(){
//        WordService wordService = mock(WordService.class);
//        when(wordService.provideRandomWord(6)).thenReturn("appels");
//
//        Game game = new Game();
//        game.startNewRound("baard");
//        game.guess("baard", game.getRounds().get(0));
//
//        SpringGameRepository repository = mock(SpringGameRepository.class);
//        when(repository.findById(anyLong())).thenReturn(Optional.of(game));
//
//        SpringRoundRepository roundRepository = mock(SpringRoundRepository.class);
//        SpringWordRepository wordRepository = mock(SpringWordRepository.class);
//        SpringProgressRepository progressRepository = mock(SpringProgressRepository.class);
//
//        TrainerService trainerService = new TrainerService(wordService, repository, roundRepository, wordRepository, progressRepository);
//        Progress progress = trainerService.startNewRound(0L);
//
//        assertEquals("a.....", progress.getHints());
//    }

    @Test
    @DisplayName("throws exception if game id is not found")
    void gameIdNotFound() {
        WordService wordService = mock(WordService.class);
        SpringRoundRepository roundRepository = mock(SpringRoundRepository.class);
        SpringGameRepository mockRepository = mock(SpringGameRepository.class);
        SpringWordRepository wordRepository = mock(SpringWordRepository.class);
        SpringProgressRepository progressRepository = mock(SpringProgressRepository.class);
        when(mockRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        TrainerService service = new TrainerService(wordService, mockRepository, roundRepository, wordRepository, progressRepository);

        assertThrows(
                GameIdNotFoundException.class,
                () -> service.getGameById((long) 1));
    }

    @Test
    @DisplayName("throws exception if round id is not found")
    void roundIdNotFound() {
        WordService wordService = mock(WordService.class);
        SpringGameRepository mockRepository = mock(SpringGameRepository.class);
        SpringRoundRepository roundRepository = mock(SpringRoundRepository.class);
        SpringWordRepository wordRepository = mock(SpringWordRepository.class);
        SpringProgressRepository progressRepository = mock(SpringProgressRepository.class);
        when(roundRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        TrainerService service = new TrainerService(wordService, mockRepository, roundRepository, wordRepository, progressRepository);

        assertThrows(
                RoundIdNotFoundException.class,
                () -> service.getRoundById((long) 1));
    }


}