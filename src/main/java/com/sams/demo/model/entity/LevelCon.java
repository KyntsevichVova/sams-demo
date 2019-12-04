package com.sams.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "LEVEL_CON")
@Table(name = "LEVEL_CON")
@Data
public class LevelCon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LEVEL_ID", nullable = false)
    private Long levelId;

    @Enumerated(EnumType.STRING)
    @Column(name = "LEVEL_TYPE", nullable = false)
    private LevelType levelType;

    @OneToMany(mappedBy = "level")
    @JsonIgnore
    private List<Question> questionList;

    @OneToMany(mappedBy = "levelCon")
    private List<LevelLocalized> levelLocalizedList;

    public enum LevelType {

        JUNIOR,
        MIDDLE,
        SENIOR
    }

}
