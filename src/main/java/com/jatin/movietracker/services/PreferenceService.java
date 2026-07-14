package com.jatin.movietracker.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jatin.movietracker.dtos.requests.GetRandomMovieRequest;
import com.jatin.movietracker.entities.User;
import com.jatin.movietracker.entities.UserPreference;
import com.jatin.movietracker.repositories.PreferenceRepository;
import com.jatin.movietracker.utils.MovieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreferenceService {

    private final PreferenceRepository repository;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public GetRandomMovieRequest getMyPreferences() {

        User user = userService.getCurrentUser();
        return repository.findByUser(user)
                .map(pref -> {
                    try {
                        return objectMapper.readValue(
                                pref.getPreferenceJson(),
                                GetRandomMovieRequest.class
                        );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElseGet(MovieUtils::getDefaultRequest);
    }


    public void savePreferences(GetRandomMovieRequest request) {
        User user = userService.getCurrentUser();
        UserPreference pref = repository.findByUser(user).orElse(new UserPreference());
        pref.setUser(user);
        try {
            pref.setPreferenceJson(objectMapper.writeValueAsString(request));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        repository.save(pref);
    }
}
