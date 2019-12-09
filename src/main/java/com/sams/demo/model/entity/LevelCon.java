package com.sams.demo.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "LEVEL")
@Table(name = "LEVEL")
@Data
@EqualsAndHashCode(of = {"id", "type"}, callSuper = false)
@ToString(of = {"id", "type"})
public class LevelCon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "LEVEL_ID", nullable = false)
    private Long id;

    @Enumerated(STRING)
    @Column(name = "TYPE", nullable = false)
    private LevelType type;

    @OneToMany(mappedBy = "level")
    private List<LevelLocalized> localizedLevels;

    public enum LevelType {

        JUNIOR,
        MIDDLE,
        SENIOR
    }
}