package fr.aves.sitecrawler.domain.services;

import fr.aves.sitecrawler.domain.entity.Arrete;
import io.vavr.control.Either;

import java.util.List;

public interface ArreteService {
    Either<Exception, Arrete> getArrete(Long id);
    Arrete updateArrete(Arrete arrete);
    List<Arrete> getAllArretes();
    Arrete createArrete(Arrete arrete);

    void deleteArrete(Long id);
}
