package com.dmytro.language_learning_api.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "translations",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"word_id", "target_language", "text"})}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Translation {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    // lenguaje de traducción: "es", "uk" y etc.
    @Column(name = "target_language", nullable = false, length = 10)
    private String targetLanguage;

    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "word_id")
    private Words word;

    // recurso (user/manual, deepl, openai)
    @Column(nullable = true)
    private String source;
}
