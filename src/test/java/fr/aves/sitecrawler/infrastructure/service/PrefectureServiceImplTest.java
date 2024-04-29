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
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PrefectureServiceImplTest {

    private final ArreteService arreteService = mock();
    private final PrefectureRepository prefectureRepository = mock();
    private final Reader reader = mock();

    private final PrefectureService prefectureService = new PrefectureServiceImpl(prefectureRepository, arreteService, reader);

    private final Prefecture prefectureGironde = new Prefecture(0L, "Gironde", List.of(), ArreteSource.BROWSE, "", "", "");

    private final Prefecture prefectureSeine = new Prefecture(0L, "Seine", List.of(), ArreteSource.BROWSE, "", "", "");

    @Test
    void should_return_either_right_with_a_prefecture() {
        // Given
        when(prefectureRepository.findById(anyLong())).thenReturn(prefectureGironde);

        // When
        val response = prefectureService.getPrefecture(0L);

        // Then
        assertThat(response).isEqualTo(Either.right(prefectureGironde));
        verify(prefectureRepository).findById(0L);
    }

    @Test
    void should_all_prefectures() {
        // Given
        final var prefectures = List.of(prefectureGironde, prefectureSeine);
        when(prefectureRepository.findAll()).thenReturn(prefectures);

        // When
        val response = prefectureService.getAllPrefectures();

        // Then
        assertThat(response).isEqualTo(prefectures);
        verify(prefectureRepository).findAll();
    }

    @Test
    void should_return_either_left() {
        // Given
        final var noSuchElement = new NoSuchElementException();
        when(prefectureRepository.findById(anyLong())).thenThrow(noSuchElement);

        // When
        val response = prefectureService.getPrefecture(0L);

        // Then
        assertThat(response.isLeft()).isTrue();
        verify(prefectureRepository).findById(0L);
    }

    @Test
    void should_create_and_return_a_prefecture() {
        // Given
        when(prefectureRepository.save(any())).thenReturn(prefectureGironde);

        // When
        val response = prefectureService.createPrefecture(prefectureGironde);

        // Then
        assertThat(response).isEqualTo(prefectureGironde);
        verify(prefectureRepository).save(prefectureGironde);
    }

    @Test
    void should_update_and_return_a_prefecture() {
        // Given
        prefectureGironde.setSource(ArreteSource.SEARCH_ENGINE);
        prefectureGironde.setNom("Updated Name");
        when(prefectureRepository.findById(anyLong())).thenReturn(prefectureGironde);
        when(prefectureRepository.save(any())).thenReturn(prefectureGironde);

        // When
        val response = prefectureService.updatePrefecture(0L, prefectureGironde);

        // Then
        assertThat(response).isEqualTo(prefectureGironde);
        verify(prefectureRepository).findById(0L);
        verify(prefectureRepository).save(prefectureGironde);
    }

    @Test
    void should_delete_a_prefecture() {
        // Given
        doNothing().when(prefectureRepository).deleteById(anyLong());

        // When
        prefectureService.deletePrefecture(0L);

        // Then
        verify(prefectureRepository).deleteById(0L);
    }

    @Test
    void should_call_to_crawl_a_prefecture_site_and_update_arretes() throws IOException {
        // Given
        when(prefectureRepository.findById(anyLong())).thenReturn(prefectureGironde);
        final var doc = new Document("some-uri");
        when(reader.connectAndRead(anyString())).thenReturn(doc);
        final var attributes = List.of(new Attribute("key", "value"));
        final var date = new Date();
        final var arretes = attributes.stream().map(attribute -> new Arrete(null, prefectureGironde, "", date, attribute.getValue())).toList();
        when(reader.extractArretes(any(), anyString())).thenReturn(attributes);
        doNothing().when(arreteService).updatePrefectureArretes(anyList());

        // When
        prefectureService.crawlSite(0L);

        // Then
        verify(prefectureRepository).findById(0L);
        verify(reader).connectAndRead(prefectureGironde.getTargetPagePath());
        verify(reader).extractArretes(doc, "");
    }

    @Test
    void should_switch_the_source_of_a_prefecture() {
        // Given
        val prefectureGirondeWithSearchEngine = prefectureGironde;
        prefectureGirondeWithSearchEngine.setSource(ArreteSource.SEARCH_ENGINE);
        when(prefectureRepository.findById(anyLong())).thenReturn(prefectureGirondeWithSearchEngine);
        when(prefectureRepository.save(any())).thenReturn(prefectureGironde);

        // When
        prefectureService.switchSource(0L, ArreteSource.SEARCH_ENGINE);

        // Then
        verify(prefectureRepository).findById(0L);
        verify(prefectureRepository).save(prefectureGirondeWithSearchEngine);
    }
}