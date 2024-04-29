package fr.aves.sitecrawler.infrastructure.service;

import fr.aves.sitecrawler.domain.entity.Arrete;
import fr.aves.sitecrawler.domain.entity.ArreteSource;
import fr.aves.sitecrawler.domain.entity.Prefecture;
import fr.aves.sitecrawler.domain.repository.PrefectureRepository;
import fr.aves.sitecrawler.domain.services.ArreteService;
import fr.aves.sitecrawler.domain.services.PrefectureService;
import fr.aves.sitecrawler.infrastructure.remote.Reader;
import io.vavr.control.Either;
import lombok.val;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PrefectureServiceImpl implements PrefectureService {

    private final PrefectureRepository prefectureRepository;

    private final ArreteService arreteService;
    private final Reader reader;
    private final Logger log = LoggerFactory.getLogger(PrefectureServiceImpl.class);

    public PrefectureServiceImpl(PrefectureRepository prefectureRepository, ArreteService arreteService, Reader reader) {
        this.prefectureRepository = prefectureRepository;
        this.arreteService = arreteService;
        this.reader = reader;
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
        currentPrefecture.setTargetPagePath(prefecture.getTargetPagePath());
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

    @Override
    public void crawlSite(Long id) {
        final var prefecture = findPrefecture(id);
        val currentPath = prefecture.getTargetPagePath();
        try {
            val doc = (Document) reader.connectAndRead(currentPath);
            final val attributes = reader.extractArretes(doc, prefecture.getTargetElement());
            arreteService.updatePrefectureArretes(attributes.stream().map(attribute -> new Arrete(null, prefecture, "", new Date(), attribute.getValue())).toList());
        } catch (Exception e)  {
            log.warn("There was an issue getting content for " + currentPath, e);
        }
    }
}
