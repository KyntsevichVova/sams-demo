package com.sams.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "LEVEL_LOCALIZED")
@Table(name = "LEVEL_LOCALIZED")
@Data
public class LevelLocalized extends BaseEntity {

    @AttributeOverrides({
            @AttributeOverride(name = "levelId", column = @Column(name = "LEVEL_ID")),
            @AttributeOverride(name = "localeId", column = @Column(name = "LOCALE_ID"))
    })
    @EmbeddedId
    private LevelLocalizedId levelLocalizedId;

    @ManyToOne
    @MapsId("levelId")
    @JoinColumn(name = "LEVEL_ID", referencedColumnName = "LEVEL_ID", updatable = false, insertable = false)
    @JsonIgnore
    private LevelCon levelCon;

    @ManyToOne
    @MapsId("localeId")
    @JoinColumn(name = "LOCALE_ID", referencedColumnName = "LOCALE_ID", updatable = false, insertable = false)
    private Locale locale;

    @Column(name = "LEVEL_LOCALIZED", nullable = false, length = 10)
    private String levelLocalized;

}
