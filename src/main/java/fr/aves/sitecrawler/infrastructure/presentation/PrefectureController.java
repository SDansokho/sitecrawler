package fr.aves.sitecrawler.infrastructure.presentation;

import fr.aves.sitecrawler.domain.services.ArreteService;
import fr.aves.sitecrawler.domain.services.PrefectureService;
import org.springframework.stereotype.Controller;

@Controller
public class PrefectureController {
    private final PrefectureService prefectureService;

    public PrefectureController(PrefectureService prefectureService) {
        this.prefectureService = prefectureService;
    }
}
