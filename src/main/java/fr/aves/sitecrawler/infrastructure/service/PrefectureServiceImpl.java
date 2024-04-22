package fr.aves.sitecrawler.infrastructure.service;

import fr.aves.sitecrawler.domain.entity.ArreteSource;
import fr.aves.sitecrawler.domain.entity.Prefecture;
import fr.aves.sitecrawler.domain.repository.PrefectureRepository;
import fr.aves.sitecrawler.domain.services.PrefectureService;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PrefectureServiceImpl implements PrefectureService {

    private final PrefectureRepository prefectureRepository;

    public PrefectureServiceImpl(PrefectureRepository prefectureRepository) {
        this.prefectureRepository = prefectureRepository;
    }

    @Override
    public Either<Exception, Prefecture> getPrefecture(Long id) {
        try {
            return Either.right(findPrefecture(id));
        } catch (Exception exception) {
            return Either.left(exception);
        }
    }

    @Override
    public Prefecture createPrefecture(Prefecture prefecture) {
        return prefectureRepository.save(prefecture);
    }

    @Override
    public void deletePrefecture(Long id) {
        prefectureRepository.deleteById(id);
    }

    @Override
    public Prefecture updatePrefecture(Long id, Prefecture prefecture) {
        var currentPrefecture = findPrefecture(id);
        currentPrefecture.setArretes(prefecture.getArretes());
        currentPrefecture.setNom(prefecture.getNom());
        currentPrefecture.setSource(prefecture.getSource());
        currentPrefecture.setTargetPath(prefecture.getTargetPath());
        return prefectureRepository.save(currentPrefecture);
    }

    private Prefecture findPrefecture(Long id) {
        return prefectureRepository.findById(id);
    }

    @Override
    public List<Prefecture> getAllPrefectures() {
        return prefectureRepository.findAll();
    }

    @Override
    public void switchSource(Long id, ArreteSource source) {
        var currentPrefecture = findPrefecture(id);
        currentPrefecture.setSource(source);
        prefectureRepository.save(currentPrefecture);
    }
}
