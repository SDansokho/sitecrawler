package fr.aves.sitecrawler.domain.services;

import fr.aves.sitecrawler.domain.entity.ArreteSource;
import fr.aves.sitecrawler.domain.entity.Prefecture;
import io.vavr.control.Either;

import java.util.List;

public interface PrefectureService {
    Either<Exception, Prefecture> getPrefecture(Long id);
    Prefecture createPrefecture(Prefecture prefecture);
    void deletePrefecture(Long id);
    Prefecture updatePrefecture(Long id, Prefecture prefecture);
    List<Prefecture> getAllPrefectures();
    void switchSource(Long id, ArreteSource source);
    void crawlSite(Long id);

}
