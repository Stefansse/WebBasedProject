package com.example.webbaziraniproekt.web;

import com.example.webbaziraniproekt.model.Athlete;
import com.example.webbaziraniproekt.repository.AthleteRepository;
import com.example.webbaziraniproekt.service.AthleteService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
@RequestMapping("/athletes")
public class AthleteController {

    @Autowired
    private AthleteService athleteService;

    @Autowired
    private AthleteRepository repository;

    @GetMapping
    public String listAthletes(Model model) {
        model.addAttribute("athletes", athleteService.getAllAthletes());
        return "list";
    }

    @GetMapping("/new")
    public String createAthleteForm(Model model) {
        model.addAttribute("athlete", new Athlete());
        return "new";
    }

    @PostMapping
    public String saveAthlete(@ModelAttribute Athlete athlete) {
        athleteService.saveAthlete(athlete);
        return "redirect:/athletes";
    }

    @GetMapping("/{id}")
    public String getAthlete(@PathVariable Long id, Model model) {
        Optional<Athlete> athlete = athleteService.getAthleteById(id);
        if (athlete.isPresent()) {
            model.addAttribute("athlete", athlete.get());
            return "view";
        } else {
            return "redirect:/athletes";
        }
    }

    @GetMapping("/{id}/edit")
    public String editAthleteForm(@PathVariable Long id, Model model) {
        Optional<Athlete> athlete = athleteService.getAthleteById(id);
        if (athlete.isPresent()) {
            model.addAttribute("athlete", athlete.get());
            return "athletesedit";
        } else {
            return "redirect:/athletes";
        }
    }

    @PostMapping("/{id}")
    public String updateAthlete(@PathVariable Long id, @ModelAttribute Athlete athlete) {
        athlete.setId(id);
        athleteService.saveAthlete(athlete);
        return "redirect:/athletes";
    }

    @PostMapping("/{id}/delete")
    public String deleteAthlete(@PathVariable Long id) {
        athleteService.deleteAthlete(id);
        return "redirect:/athletes";
    }

    @GetMapping("/{id}/pie-chart")
    public String showPlayerPieChart(@PathVariable Long id, Model model) {
        // Fetch the specific athlete by ID
        Athlete athlete = repository.findById(id).orElse(null);
        if (athlete == null) {
            return "redirect:/error"; // or any other error handling
        }

        // Assuming you have a method to get career statistics for the specific player
        int careerGoals = athlete.getCareerGoals();
        int careerMatches = athlete.getCareerMatches();

        // Pass data to the view
        model.addAttribute("athleteName", athlete.getName());
        model.addAttribute("careerGoals", careerGoals);
        model.addAttribute("careerMatches", careerMatches);

        return "player-pie-chart";
    }

    @PostMapping("/transfer-data")
    public String transferData(@RequestBody String data, Model model) {
        try {

            String decodedData = URLDecoder.decode(data, StandardCharsets.UTF_8.toString());


            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, String>> resultsData = objectMapper.readValue(decodedData, new TypeReference<List<Map<String, String>>>() {});


            List<Athlete> athletes = parseResultsData(resultsData);


            for (Athlete athlete : athletes) {
                athleteService.saveAthlete(athlete);
            }

            model.addAttribute("success", "Data transferred successfully.");
        } catch (Exception e) {
            model.addAttribute("error", "Error transferring data: " + e.getMessage());
        }

        return "redirect:/athletes";
    }


    private List<Athlete> parseResultsData(List<Map<String, String>> data) {
        List<Athlete> athletes = new ArrayList<>();

        for (Map<String, String> entry : data) {
            Athlete athlete = new Athlete();

            // Extract and set fields from the map
//            athlete.setPlayerName(extractValue(entry, "playerLabel"));
//            athlete.setCountryLabel(extractValue(entry, "countryLabel"));
//            athlete.setBirthDate(extractValue(entry, "birthDate"));
            // Add other fields as necessary

            athletes.add(athlete);
        }

        return athletes;
    }

    private String extractValue(Map<String, String> entry, String key) {
        String value = entry.get(key);
        if (value != null && value.contains("=")) {
            return value.split("=")[1].split("@")[0]; // Extract value from the format `key=value@lang`
        }
        return value;
    }

}