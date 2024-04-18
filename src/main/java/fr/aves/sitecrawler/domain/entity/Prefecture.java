package fr.aves.sitecrawler.domain.entity;

import java.util.List;

public record Prefecture(
        Long id,
        String nom,
        List<Arrete> arretes,
        ArreteSource source
) {
}
