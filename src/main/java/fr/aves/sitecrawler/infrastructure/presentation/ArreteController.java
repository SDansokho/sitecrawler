package fr.aves.sitecrawler.infrastructure.presentation;

import fr.aves.sitecrawler.domain.entity.Arrete;
import fr.aves.sitecrawler.domain.services.ArreteService;
import io.vavr.control.Either;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/arrete")
public class ArreteController {
    private final ArreteService arreteService;

    public ArreteController(ArreteService arreteService) {
        this.arreteService = arreteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Arrete> getArrete(@PathVariable("id") Long id) {
        var response = arreteService.getArrete(id);
        switch (response) {
            case Either.Right<Exception, Arrete> right -> {
                return ok(right.get());
            }
            default -> {
                return noContent().build();
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteArrete(@PathVariable Long id) {
        arreteService.deleteArrete(id);
        return ok().build();
    }
}
