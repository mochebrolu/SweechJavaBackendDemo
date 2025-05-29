package com.sweech.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rankings")
public class RankingController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/weekly")
    public ResponseEntity<?> weeklyRanking() {
        return ResponseEntity.ok(loginService.getWeeklyRanking());
    }
}
