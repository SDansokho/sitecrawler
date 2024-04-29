package fr.aves.sitecrawler.infrastructure.presentation;

import fr.aves.sitecrawler.domain.entity.ArreteSource;
import fr.aves.sitecrawler.domain.entity.Prefecture;
import fr.aves.sitecrawler.domain.services.PrefectureService;
import io.vavr.control.Either;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/prefecture")
public class PrefectureController {
    private final PrefectureService prefectureService;

    public PrefectureController(PrefectureService prefectureService) {
        this.prefectureService = prefectureService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prefecture> getPrefecture(@PathVariable("id") Long id) {
        var response = prefectureService.getPrefecture(id);
        if (Objects.requireNonNull(response) instanceof Either.Right<Exception, Prefecture> right) {
            return ResponseEntity.ok(right.get());
        }
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<Prefecture>> getAllPrefectures() {
        var response = prefectureService.getAllPrefectures();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Prefecture> createPrefecture(@RequestBody Prefecture prefecture) {
        var response = prefectureService.createPrefecture(prefecture);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prefecture> updatePrefecture(@PathVariable("id") Long id, @RequestBody Prefecture prefecture) {
        var response = prefectureService.updatePrefecture(id, prefecture);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity switchSource(@PathVariable("id") Long id, @RequestBody ArreteSource source) {
        prefectureService.switchSource(id, source);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePrefecture(@PathVariable("id") Long id) {
        prefectureService.deletePrefecture(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/content")
    public ResponseEntity<Object> readSite(@PathVariable("id")Long id) {
        prefectureService.crawlSite(id);
        return ResponseEntity.noContent().build();
    }


}
