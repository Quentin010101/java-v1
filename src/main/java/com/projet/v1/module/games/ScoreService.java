package com.projet.v1.module.games;

import com.projet.v1.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;

    public List<ScoreDto> getAll(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ScoreDao> list = scoreRepository.findAllByUser(user);

        return list.stream().map(this::mapperScoreToDto).collect(Collectors.toList());
    }

    public ScoreDto newScore(NewScoreDto newScore){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ScoreDao score = new ScoreDao();
        score.setHighScore(newScore.score());
        score.setGameType(newScore.type());
        score.setUser(user);
        return mapperScoreToDto(scoreRepository.save(score));
    }

    public ScoreDto updateScore(ScoreDto s){
        ScoreDao scoreDao = scoreRepository.findById(s.scoreId()).orElseThrow();
        if(s.type() != scoreDao.getGameType()) throw new RuntimeException();
        log.info("score");
        log.info(scoreDao.getHighScore().toString());
        log.info(s.score().toString());
        if(scoreDao.getHighScore() < s.score()){
            scoreDao.setHighScore(s.score());
            return mapperScoreToDto(scoreRepository.save(scoreDao));
        }
        return mapperScoreToDto(scoreDao);
    }

    private ScoreDto mapperScoreToDto(ScoreDao s){
        return new ScoreDto(s.getScoreId(),s.getGameType(),s.getHighScore());
    }
    private ScoreDao mapperScoreToDao(ScoreDto s, User user){
        return new ScoreDao(s.scoreId(),s.score(),s.type(), user);
    }
}
