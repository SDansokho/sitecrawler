package fr.aves.sitecrawler.domain.repository;

import fr.aves.sitecrawler.domain.entity.Arrete;
import fr.aves.sitecrawler.domain.entity.Prefecture;

import java.util.List;

public interface PrefectureRepository {
    List<Arrete> findAll();
    Prefecture findById(Long id);
    Prefecture save(Prefecture prefecture);
    Prefecture deleteById(Long id);
}
