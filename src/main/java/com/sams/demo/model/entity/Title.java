package com.sams.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "TITLE")
@Table(name = "TITLE")
@Data
public class Title extends BaseEntity {

    @AttributeOverrides({
            @AttributeOverride(name = "localeId", column = @Column(name = "LOCALE_ID")),
            @AttributeOverride(name = "questionId", column = @Column(name = "QUESTION_ID"))
    })
    @EmbeddedId
    private TitleId titleId;

    @ManyToOne
    @MapsId("localeId")
    @JoinColumn(
            name = "LOCALE_ID", referencedColumnName = "LOCALE_ID",
            updatable = false, insertable = false
    )
    private Locale locale;

    @ManyToOne
    @MapsId("questionId")
    @JoinColumn(
            name = "QUESTION_ID", referencedColumnName = "QUESTION_ID",
            updatable = false, insertable = false
    )
    @JsonIgnore
    private Question question;

    @Column(name = "TITLE", nullable = false)
    private String title;

}
