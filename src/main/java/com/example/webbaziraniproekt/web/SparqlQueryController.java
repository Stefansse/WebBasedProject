package com.example.webbaziraniproekt.web;

import com.example.webbaziraniproekt.model.Athlete;
import com.example.webbaziraniproekt.repository.AthleteRepository;
import com.example.webbaziraniproekt.service.SparqlService;
import com.example.webbaziraniproekt.util.StringUtils;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.apache.jena.atlas.lib.RandomLib.random;

@Controller
public class SparqlQueryController {

    @Autowired
    private SparqlService sparqlService;

    @Autowired
    private AthleteRepository repository;

    @GetMapping("/query-form")
    public String showQueryForm(Model model) {
        return "query-form";
    }

    @PostMapping("/run-query")
    public String runQuery(@RequestParam("query") String query, Model model) {
        List<Map<String, String>> resultsList = new ArrayList<>();
        List<String> headersList = new ArrayList<>();


        try {
            ResultSet results = sparqlService.executeQuery(query);

            if (results.hasNext()) {

                headersList = results.getResultVars();


                while (results.hasNext()) {
                    QuerySolution solution = results.nextSolution();
                    Map<String, String> resultMap = new HashMap<>();
                    for (String header : headersList) {
                        if (solution.contains(header)) {
                            String rawValue = solution.get(header).toString();
                            String cleanedValue = StringUtils.cleanString(rawValue);
                            resultMap.put(header, cleanedValue);
                        } else {
                            resultMap.put(header, "N/A");
                        }
                    }
                    resultsList.add(resultMap);

                    Athlete athlete = new Athlete();


                    String playerLabel = solution.contains("playerLabel") ? StringUtils.cleanString(solution.get("playerLabel").toString()) : "Unknown";
                    String birthDateStr = solution.contains("birthDate") ? StringUtils.cleanString(solution.get("birthDate").toString()) : "1970-01-01";
                    String countryLabel = solution.contains("countryLabel") ? StringUtils.cleanString(solution.get("countryLabel").toString()) : "Unknown";
                    String height = solution.contains("height") ? StringUtils.cleanString(solution.get("height").toString()) : "Unknown";
                    String weight = solution.contains("weight") ? StringUtils.cleanString(solution.get("weight").toString()) : "Unknown";
                    String team = solution.contains("teamLabel") ? StringUtils.cleanString(solution.get("teamLabel").toString()) : "Unknown";
                    String position = solution.contains("positionLabel") ? StringUtils.cleanString(solution.get("positionLabel").toString()) : "Unknown";
                    int mvpAwards = solution.contains("mvpAwards") ? Integer.parseInt(StringUtils.cleanString(solution.get("mvpAwards").toString())) : 0;
                    String jerseyNumber = solution.contains("jerseyNumber") ? StringUtils.cleanString(solution.get("jerseyNumber").toString()) : "Unknown";
                    String sportLabel = solution.contains("sportLabel") ? StringUtils.cleanString(solution.get("sportLabel").toString()) : "Unknown";

                    int careerGoals = solution.contains("careerGoals") ? Integer.parseInt(StringUtils.cleanString(solution.get("careerGoals").toString())) : generateRandomNumber(10, 100);
                    int careerMatches = solution.contains("careerMatches") ? Integer.parseInt(StringUtils.cleanString(solution.get("careerMatches").toString())) : generateRandomNumber(10, 300);


                    athlete.setName(playerLabel);
                    athlete.setBirthDate(birthDateStr);
                    athlete.setCountry(countryLabel);
                    athlete.setHeight(height);
                    athlete.setWeight(weight);
                    athlete.setTeam(team);
                    athlete.setPosition(position);
                    athlete.setMvpAwards(mvpAwards);
                    athlete.setJerseyNumber(jerseyNumber);
                    athlete.setSportLabel(sportLabel);
                    athlete.setCareerGoals(careerGoals);
                    athlete.setCareerMatches(careerMatches);


                    repository.save(athlete);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error executing query: " + e.getMessage());
        }

        model.addAttribute("headers", headersList);
        model.addAttribute("results", resultsList);
        return "query-form";
    }

    private static int generateRandomNumber(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

}
