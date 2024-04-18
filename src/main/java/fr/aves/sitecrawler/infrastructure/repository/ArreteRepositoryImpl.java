package fr.aves.sitecrawler.infrastructure.repository;

import fr.aves.sitecrawler.domain.entity.Arrete;
import fr.aves.sitecrawler.domain.repository.ArreteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArreteRepositoryImpl implements ArreteRepository {

    private final ArreteJpaRepository arreteJpaRepository;

    public ArreteRepositoryImpl(ArreteJpaRepository arreteJpaRepository) {
        this.arreteJpaRepository = arreteJpaRepository;
    }

    @Override
    public List<Arrete> findAll() {
        return null;
    }

    @Override
    public Arrete findById(Long id) {
        return null;
    }

    @Override
    public Arrete save(Arrete arrete) {
        return null;
    }

    @Override
    public Arrete deleteById(Long id) {
        return null;
    }
}
