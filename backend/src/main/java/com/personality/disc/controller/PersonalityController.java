package com.personality.disc.controller;

import com.personality.disc.model.*;
import com.personality.disc.service.PersonalityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PersonalityController {

    private final PersonalityService service;

    public PersonalityController(PersonalityService service) {
        this.service = service;
    }

    @GetMapping("/questions")
    public List<QuestionRound> getQuestions() {
        return service.getQuestions();
    }

    @PostMapping("/evaluate")
    public TestResult evaluate(@RequestBody TestAnswers answers) {
        return service.evaluate(answers);
    }

    @GetMapping("/personalities")
    public List<PersonalityInfo> getPersonalities() {
        return service.getAllPersonalities();
    }

    @GetMapping("/personalities/{key}")
    public PersonalityInfo getPersonality(@PathVariable String key) {
        return service.getPersonality(key);
    }
}
