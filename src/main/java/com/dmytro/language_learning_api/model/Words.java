package com.dmytro.language_learning_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "words")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Words {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    // Язык исходного слова: рекомендуем ISO code, напр. "en", "es", "uk"
    @Column(nullable = false, length = 2)
    @Pattern(regexp = "^[a-z]{2}(-[A-Z]{2})?$", message = "Language must be an ISO code like 'en' or 'en-US'")
    private String sourceLanguage;

    @Column(nullable = false)
    private String originalWord;

    // Dueño de palabra
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id")
    private Users owner;

    // Lista de traducción (muchos traducciónes — una entidad 'Translation')
    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Translation> translations;

}
