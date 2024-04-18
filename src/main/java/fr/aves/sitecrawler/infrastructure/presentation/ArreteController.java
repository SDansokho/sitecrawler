package fr.aves.sitecrawler.infrastructure.presentation;

import fr.aves.sitecrawler.domain.services.ArreteService;
import org.springframework.stereotype.Controller;

@Controller
public class ArreteController {
    private final ArreteService arreteService;

    public ArreteController(ArreteService arreteService) {
        this.arreteService = arreteService;
    }
}
