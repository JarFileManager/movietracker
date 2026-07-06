package com.jatin.movietracker.controllers;

import com.jatin.movietracker.dtos.requests.GetRandomMovieRequest;
import com.jatin.movietracker.services.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/preferences")
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferenceService preferenceService;

    @GetMapping
    public ResponseEntity<GetRandomMovieRequest> getPreferences() {
        return ResponseEntity.ok(preferenceService.getMyPreferences());
    }

    @PutMapping
    public ResponseEntity<Void> savePreferences(@RequestBody GetRandomMovieRequest request) {
        preferenceService.savePreferences(request);
        return ResponseEntity.ok().build();
    }

}
