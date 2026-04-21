package by.system.aibothelper.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "legends")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LegendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(columnDefinition = "TEXT")
    String originalText;

    @Column(columnDefinition = "TEXT")
    String rewrittenText;

    int score;

    boolean strong;

    LocalDateTime createdAt;
}