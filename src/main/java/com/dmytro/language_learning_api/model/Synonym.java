package com.dmytro.language_learning_api.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
@Table(name = "synonyms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Synonym {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String synonymText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "translation_id")
    private Translation translation;
}
