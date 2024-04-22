package fr.aves.sitecrawler.infrastructure.repository;

import fr.aves.sitecrawler.domain.entity.ArreteSource;
import fr.aves.sitecrawler.domain.entity.Prefecture;
import fr.aves.sitecrawler.domain.repository.PrefectureRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class PrefectureRepositoryImpl implements PrefectureRepository {

    private final PrefectureJpaRepository prefectureJpaRepository;

    public PrefectureRepositoryImpl(PrefectureJpaRepository prefectureJpaRepository) {
        this.prefectureJpaRepository = prefectureJpaRepository;
    }

    @Override
    public List<Prefecture> findAll() {
        return prefectureJpaRepository.findAll();
    }

    @Override
    public Prefecture findById(Long id) {
        return prefectureJpaRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No prefecture found with id" + id));
    }

    @Override
    public Prefecture save(Prefecture prefecture) {
        return prefectureJpaRepository.save(prefecture);
    }

    @Override
    public void deleteById(Long id) {
        prefectureJpaRepository.deleteById(id);
    }

}
