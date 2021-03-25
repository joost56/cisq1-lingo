package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.data.SpringRoundRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameIdNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundIdNotFoundException;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.exception.WordLengthNotSupportedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

class TrainerServiceTest {

    @Test
    @DisplayName("Start a new game")
    void providesGame(){
        WordService wordService = mock(WordService.class);
        SpringGameRepository gameRespository = mock(SpringGameRepository.class);
        SpringRoundRepository roundRepository = mock(SpringRoundRepository.class);
        SpringWordRepository wordRepository = mock(SpringWordRepository.class);
        TrainerService service = new TrainerService(wordService, gameRespository, roundRepository, wordRepository);
        when(wordService.provideRandomWord(5)).thenReturn("appel");
        Game game = new Game();
        game.startNewGame();
        Game result = service.startNewGame();
        assertEquals(game, result);
    }


//    @Test
//    @DisplayName("Start a new round")
//    void providesRound(){
//        WordService wordService = mock(WordService.class);
//        SpringRoundRepository mockRespository = mock(SpringRoundRepository.class);
//        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
//        SpringWordRepository wordRepository = mock(SpringWordRepository.class);
//        TrainerService service = new TrainerService(wordService, gameRepository, mockRespository, wordRepository);
//        when(wordService.provideRandomWord(5)).thenReturn("appel");
//        Game game = new Game((long)1);
//        game.startNewGame();
//        game.startNewRound("appel");
//        String result = service.startNewRound((long)1);
//        Round ronde = game.getRonde().get(0);
//
//        assertEquals(ronde.getPreviousHint(), result);
//    }

    @Test
    @DisplayName("throws exception if game id is not found")
    void gameIdNotFound() {
        WordService wordService = mock(WordService.class);
        SpringGameRepository mockRepository = mock(SpringGameRepository.class);
        SpringWordRepository wordRepository = mock(SpringWordRepository.class);
        SpringRoundRepository roundRepository = mock(SpringRoundRepository.class);
        when(mockRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        TrainerService service = new TrainerService(wordService, mockRepository, roundRepository, wordRepository);

        assertThrows(
                GameIdNotFoundException.class,
                () -> service.getGameById((long) 1));
    }

    @Test
    @DisplayName("throws exception if round id is not found")
    void roundIdNotFound() {
        WordService wordService = mock(WordService.class);
        SpringGameRepository mockRepository = mock(SpringGameRepository.class);
        SpringWordRepository wordRepository = mock(SpringWordRepository.class);
        SpringRoundRepository roundRepository = mock(SpringRoundRepository.class);
        when(roundRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        TrainerService service = new TrainerService(wordService, mockRepository, roundRepository, wordRepository);

        assertThrows(
                RoundIdNotFoundException.class,
                () -> service.getRoundById((long) 1));
    }


}