package fr.aves.sitecrawler.infrastructure.presentation;

import fr.aves.sitecrawler.domain.entity.Arrete;
import fr.aves.sitecrawler.domain.services.ArreteService;
import io.vavr.control.Either;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController("/arrete")
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

    @GetMapping()
    public ResponseEntity<List<Arrete>> getAllArretes() {
        return ok(arreteService.getAllArretes());
    }

    @PostMapping("/{id}")
    public ResponseEntity<Arrete> createArrete(@RequestBody Arrete arrete) {
        return ok(arreteService.createArrete(arrete));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Arrete> udpateArrete(@PathVariable("id") Long id,@RequestBody Arrete arrete) {
        return ok(arreteService.updateArrete(arrete));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteArrete(@PathVariable Long id) {
        arreteService.deleteArrete(id);
        return ok().build();
    }
}
