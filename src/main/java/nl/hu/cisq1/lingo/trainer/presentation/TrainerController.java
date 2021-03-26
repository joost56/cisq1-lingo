package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.presentation.DTO.TrainerDTO;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lingo")
public class TrainerController {
    private TrainerService trainerService;
    private WordService wordService;

    public TrainerController(TrainerService trainerService, WordService wordService) {
        this.trainerService = trainerService;
        this.wordService = wordService;
    }

    @PostMapping("/startGame")
    public Game startGame()
    {
        return trainerService.startNewGame();
    }

    @GetMapping(value = "/{id}")
    public Game getGameById (@PathVariable Long id) {
        Game game = this.trainerService.getGameById(id);
        return game;
    }

    @PutMapping(value = "/startRound/{id}")
    public Progress startRound(@PathVariable Long id)
    {
        return trainerService.startNewRound(id);
    }

    @PutMapping("/guess/{roundId}")
    public String guess(@PathVariable Long roundId, @RequestBody TrainerDTO trainerDTO)
    {
        return trainerService.guess(trainerDTO.attempt, roundId);
    }
}
