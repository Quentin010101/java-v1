package com.projet.v1.module.games;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("${frontend.server.url}")
@RequestMapping("/games")
@Slf4j
public class GamesControler {


}
