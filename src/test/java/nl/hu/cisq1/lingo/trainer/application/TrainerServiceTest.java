package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.data.SpringRoundRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
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
//        Game game = new Game();
//        game.startNewGame();
//        Round round = new Round();
//        when(mockRespository.save(round)).thenReturn(new Round());
//        game.startNewRound(round.getWordToGuess());
//
//        TrainerService service = new TrainerService(wordService, gameRepository, mockRespository);
//        service.startNewRound(game.getId().longValue());
//        Round result = service.startNewRound(game.getId().longValue());
//
//        assertEquals(round, result);
//    }


}