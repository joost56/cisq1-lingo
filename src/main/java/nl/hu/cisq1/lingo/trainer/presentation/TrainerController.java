package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.presentation.DTO.ProgressDTO;
import nl.hu.cisq1.lingo.trainer.presentation.DTO.TrainerDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lingo")
public class TrainerController {
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping("/startGame")
    public ProgressDTO startGame()
    {
        return trainerService.startNewGame();
    }

    @GetMapping(value = "/{id}")
    public Game getGameById (@PathVariable Long id) {
        return this.trainerService.getGameById(id);
    }

    @PutMapping(value = "/startRound/{id}")
    public ProgressDTO startRound(@PathVariable Long id)
    {
        return trainerService.startNewRound(id);
    }

    @PutMapping("/guess/{roundId}/{gameId}")
    public ProgressDTO guess(@PathVariable Long roundId, @PathVariable Long gameId, @RequestBody TrainerDTO trainerDTO)
    {
        return trainerService.guess(trainerDTO.attempt, roundId, gameId);
    }
}