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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TrainerServiceTest {
    private List<Word> words = new ArrayList<>();

    @Test
    @DisplayName("Start a new game")
    void providesGame(){
        WordService wordService = mock(WordService.class);
        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
        SpringRoundRepository roundRepository = mock(SpringRoundRepository.class);
        SpringWordRepository wordRepository = mock(SpringWordRepository.class);
        TrainerService service = new TrainerService(wordService, gameRepository, roundRepository, wordRepository);
        when(wordService.provideRandomWord(5)).thenReturn("baard");
        ProgressDTO progress = service.startNewGame();

        assertEquals("message:Take a wild guess\nscore:0\nhints:b....\nroundnumber:1", progress.toString());
    }

    @Test
    @DisplayName("Make a guess")
    void makesAGuess(){
        WordService wordService = mock(WordService.class);
        Game game = new Game("baard");

        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));

        SpringRoundRepository roundRepository = mock(SpringRoundRepository.class);
        SpringWordRepository wordRepository = mock(SpringWordRepository.class);
        TrainerService service = new TrainerService(wordService, gameRepository, roundRepository, wordRepository);

        service.startNewRound(0L);

        when(roundRepository.findById(anyLong())).thenReturn(Optional.of(game.getRounds().get(0)));
        ProgressDTO progress = service.guess("woont", 0L, 0L);
        assertEquals("message:This word does not exist! You are eliminated.\nscore:0\nhints:b....\nroundnumber:1", progress.toString());
    }

    @Test
    @DisplayName("Start a new round")
    void providesRound(){
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(6)).thenReturn("hoeden");
        Game game = new Game("baard");
        game.guess("baard", game.getRounds().get(0));

        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));

        SpringRoundRepository roundRepository = mock(SpringRoundRepository.class);
        SpringWordRepository wordRepository = mock(SpringWordRepository.class);
        TrainerService service = new TrainerService(wordService, gameRepository, roundRepository, wordRepository);

        ProgressDTO progress = service.startNewRound(0L);
        assertEquals("h.....", progress.hints);
        when(roundRepository.findById(anyLong())).thenReturn(Optional.of(game.getRounds().get(1)));
        service.guess("hoeden", 0L, 0L);
        service.startNewRound(0L);
        assertEquals(7, game.getNextWordLength());
    }

    @Test
    @DisplayName("throws exception if game id is not found")
    void gameIdNotFound() {
        WordService wordService = mock(WordService.class);
        SpringRoundRepository roundRepository = mock(SpringRoundRepository.class);
        SpringGameRepository mockRepository = mock(SpringGameRepository.class);
        SpringWordRepository wordRepository = mock(SpringWordRepository.class);
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
        SpringRoundRepository roundRepository = mock(SpringRoundRepository.class);
        SpringWordRepository wordRepository = mock(SpringWordRepository.class);
        when(roundRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        TrainerService service = new TrainerService(wordService, mockRepository, roundRepository, wordRepository);

        assertThrows(
                RoundIdNotFoundException.class,
                () -> service.getRoundById((long) 1));
    }

    @Test
    @DisplayName("throws exception if round not found")
    void roundNotFound() {
        WordService wordService = mock(WordService.class);
        SpringRoundRepository roundRepository = mock(SpringRoundRepository.class);
        SpringGameRepository mockRepository = mock(SpringGameRepository.class);
        SpringWordRepository wordRepository = mock(SpringWordRepository.class);
        when(mockRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        TrainerService service = new TrainerService(wordService, mockRepository, roundRepository, wordRepository);

        assertThrows(
                RoundnotFoundException.class,
                () -> service.getRoundsByGameId((long) 1));
    }
}