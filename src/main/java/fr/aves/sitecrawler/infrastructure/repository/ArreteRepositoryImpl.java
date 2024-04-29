package fr.aves.sitecrawler.infrastructure.repository;

import fr.aves.sitecrawler.domain.entity.Arrete;
import fr.aves.sitecrawler.domain.repository.ArreteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class ArreteRepositoryImpl implements ArreteRepository {

    private final ArreteJpaRepository arreteJpaRepository;

    public ArreteRepositoryImpl(ArreteJpaRepository arreteJpaRepository) {
        this.arreteJpaRepository = arreteJpaRepository;
    }

    @Override
    public List<Arrete> findAll() {
        return arreteJpaRepository.findAll();
    }

    @Override
    public Arrete findById(Long id) {
        return arreteJpaRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No arrete found with id  " + id));
    }

    @Override
    public Arrete save(Arrete arrete) {
        return arreteJpaRepository.save(arrete);
    }

    @Override
    public void deleteById(Long id) {
        arreteJpaRepository.deleteById(id);
    }
}
