package fr.aves.sitecrawler.infrastructure.service;

import fr.aves.sitecrawler.domain.entity.Arrete;
import fr.aves.sitecrawler.domain.repository.ArreteRepository;
import fr.aves.sitecrawler.domain.services.ArreteService;
import io.vavr.control.Either;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArreteServiceImpl implements ArreteService {

    private final ArreteRepository arreteRepository;
    private final Logger log = LoggerFactory.getLogger(ArreteService.class);

    public ArreteServiceImpl(ArreteRepository arreteRepository) {
        this.arreteRepository = arreteRepository;
    }

    @Override
    public Either<Exception,Arrete> getArrete(Long id) {
        try {
            return Either.right(findArrete(id));

        } catch (Exception e) {
            return Either.left(new NoSuchElementException("There was an issue"));
        }
    }

    private Arrete findArrete(Long id) {
        return arreteRepository.findById(id);
    }

    @Override
    public Arrete updateArrete(Arrete newArrete) {
        val currentArrete  = findArrete(newArrete.getArreteId());
        currentArrete.setDate(newArrete.getDate());
        currentArrete.setDescription(newArrete.getDescription());
        return arreteRepository.save(currentArrete);
    }

    @Override
    public List<Arrete> getAllArretes() {
        return arreteRepository.findAll();
    }

    @Override
    public Arrete createArrete(Arrete arrete) {
        return arreteRepository.save(arrete);
    }

    @Override
    public void updatePrefectureArretes(List<Arrete> arretes) {
        arretes.forEach(this::updateArret);
    }

    @Override
    public void deleteArrete(Long id) {
        arreteRepository.deleteById(id);
    }

    private void updateArret(Arrete arrete) {
        try{
            arreteRepository.save(arrete);
        } catch (DataAccessException exception) {
            log.warn("There was an issue updating arrete " + arrete.getArreteId());
        }
    }
}
