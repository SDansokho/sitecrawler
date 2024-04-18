package fr.aves.sitecrawler.domain.entity;


import java.util.Date;

public record Arrete(
        Long id,
        String description,
        Date date
) {
}
