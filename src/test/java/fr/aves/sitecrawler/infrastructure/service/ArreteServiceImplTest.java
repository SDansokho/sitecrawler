package fr.aves.sitecrawler.infrastructure.service;

import fr.aves.sitecrawler.domain.entity.Arrete;
import fr.aves.sitecrawler.domain.entity.ArreteSource;
import fr.aves.sitecrawler.domain.entity.Prefecture;
import fr.aves.sitecrawler.domain.repository.ArreteRepository;
import fr.aves.sitecrawler.domain.services.ArreteService;
import io.vavr.control.Either;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ArreteServiceImplTest {
    private final ArreteRepository arreteRepository = mock();
    private final ArreteService arreteService = new ArreteServiceImpl(arreteRepository);
    private final Arrete arrete = new Arrete(0L, new Prefecture(0L, "Some-prefecture", List.of(), ArreteSource.BROWSE, "", "", ""), "some-description", new Date(), "");

    @Test
    void should_return_a_right_either_with_an_arrete() {
        // Given
        when(arreteRepository.findById(anyLong())).thenReturn(arrete);

        // When
        val foundArrete = arreteService.getArrete(0L);

        // Then
        assertThat(foundArrete).isEqualTo(Either.right(arrete));
        verify(arreteRepository).findById(0L);
    }

    @Test
    void should_return_a_left_either() {
        // Given
        final var exception = new NoSuchElementException("There was an issue");
        when(arreteRepository.findById(anyLong())).thenThrow(exception);

        // When
        val response = arreteService.getArrete(0L);

        // Then
        assertThat(response.isLeft()).isTrue();
        verify(arreteRepository).findById(0L);

    }

    @Test
    void should_delete_an_arrete() {
        // Given
        doNothing().when(arreteRepository).deleteById(anyLong());

        // When
        arreteService.deleteArrete(0L);

        // Then
        verify(arreteRepository).deleteById(0L);

    }
}