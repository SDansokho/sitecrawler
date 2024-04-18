package fr.aves.sitecrawler.domain.repository;

import fr.aves.sitecrawler.domain.entity.Arrete;

import java.util.List;

public interface ArreteRepository {
    List<Arrete> findAll();
    Arrete findById(Long id);
    Arrete save(Arrete arrete);
    Arrete deleteById(Long id);
}
