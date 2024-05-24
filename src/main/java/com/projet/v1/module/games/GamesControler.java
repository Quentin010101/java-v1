package com.projet.v1.module.games;

import com.projet.v1.dto.ResponseDto;
import com.projet.v1.dto.ResponseObjectDto;
import com.projet.v1.security.administration.ModuleService;
import com.projet.v1.security.administration.userConfiguration.ModuleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@CrossOrigin("${frontend.server.url}")
@RequestMapping("/games")
@Slf4j
public class GamesControler {

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private ModuleService moduleService;

    @GetMapping("/all")
    public ResponseObjectDto<List<ScoreDto>> getAllScores() throws AccessDeniedException {
        if(!moduleService.hasModule(ModuleEnum.GAMES)) throw new AccessDeniedException("Planner module missing.");

        List<ScoreDto> scores = scoreService.getAll();
        return new ResponseObjectDto<>(new ResponseDto("All scores", true), scores);
    }

    @PostMapping("/new")
    public ResponseObjectDto<ScoreDto> generateNewScore(@RequestBody NewScoreDto score) throws AccessDeniedException {
        if(!moduleService.hasModule(ModuleEnum.GAMES)) throw new AccessDeniedException("Planner module missing.");

        log.info("new Score " + score.toString());
        ScoreDto scoreDto = scoreService.newScore(score);
        return new ResponseObjectDto<>(new ResponseDto("New score", true), scoreDto);
    }

    @PostMapping("/update")
    public ResponseObjectDto<ScoreDto> updateScore(@RequestBody ScoreDto score) throws AccessDeniedException {
        if(!moduleService.hasModule(ModuleEnum.GAMES)) throw new AccessDeniedException("Planner module missing.");

        ScoreDto scoreDto = scoreService.updateScore(score);
        return new ResponseObjectDto<>(new ResponseDto("score updated", true), scoreDto);
    }
}
