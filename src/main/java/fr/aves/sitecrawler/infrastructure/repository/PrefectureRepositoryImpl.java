package fr.aves.sitecrawler.infrastructure.repository;

import fr.aves.sitecrawler.domain.entity.Arrete;
import fr.aves.sitecrawler.domain.entity.Prefecture;
import fr.aves.sitecrawler.domain.repository.PrefectureRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrefectureRepositoryImpl implements PrefectureRepository {

    private final PrefectureJpaRepository prefectureJpaRepository;

    public PrefectureRepositoryImpl(PrefectureJpaRepository prefectureJpaRepository) {
        this.prefectureJpaRepository = prefectureJpaRepository;
    }

    @Override
    public List<Arrete> findAll() {
        return null;
    }

    @Override
    public Prefecture findById(Long id) {
        return null;
    }

    @Override
    public Prefecture save(Prefecture prefecture) {
        return null;
    }

    @Override
    public Prefecture deleteById(Long id) {
        return null;
    }
}
