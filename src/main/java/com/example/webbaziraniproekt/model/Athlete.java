package com.example.webbaziraniproekt.model;
import com.example.webbaziraniproekt.enumerations.Sport;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "athletes")
public class Athlete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String birthDate;
    private String country;
    private String height;
    private String weight;
    private String team;
    private String position;
    private int mvpAwards;
    private String jerseyNumber;
    private String sportLabel;

    private Integer careerGoals;
    private Integer careerMatches;


}
