package fr.aves.sitecrawler.infrastructure.repository;

import fr.aves.sitecrawler.domain.entity.Arrete;
import fr.aves.sitecrawler.domain.entity.ArreteSource;
import fr.aves.sitecrawler.domain.entity.Prefecture;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ArreteRepositoryImplTest {

    private final ArreteJpaRepository arreteJpaRepository = mock();
    private final ArreteRepositoryImpl arreteRepository = new ArreteRepositoryImpl(arreteJpaRepository);

    private Arrete arretePrefecturalGironde = new Arrete(0L,new Prefecture(0L, "Some-prefecture", List.of(), ArreteSource.BROWSE, "", "", ""), "some-desc", new Date(), "");
    private Arrete arretePrefecturalSeine = new Arrete(1L,new Prefecture(1L, "Some-other-prefecture", List.of(), ArreteSource.BROWSE, "", "", ""), "some-other-desc", new Date(), "");

    @Test
    void should_find_all_arretes() {
        //Given
        final var expectedList = Arrays.asList(arretePrefecturalGironde, arretePrefecturalSeine);
        Mockito.when(arreteJpaRepository.findAll()).thenReturn(expectedList);

        // When
        val response = arreteRepository.findAll();

        // Then
        assertThat(response).isEqualTo(expectedList);
        Mockito.verify(arreteJpaRepository).findAll();
    }

    @Test
    void should_return_arrete() {
        //Given
        when(arreteJpaRepository.findById(anyLong())).thenReturn(Optional.of(arretePrefecturalGironde));

        // When
        val response = arreteRepository.findById(0L);

        // Then
        assertThat(response).isEqualTo(arretePrefecturalGironde);
        verify(arreteJpaRepository).findById(0L);
    }

    @Test
    void should_throw_exception() {
        //Given
        when(arreteJpaRepository.findById(anyLong())).thenThrow(new NoSuchElementException("No arrete found with id" + 0L));

        // When/Then
        assertThrows(NoSuchElementException.class, () -> arreteRepository.findById(0L));
        verify(arreteJpaRepository).findById(0L);

    }

    @Test
    void should_save_and_return_arrete() {
        //Given
        when(arreteJpaRepository.save(any())).thenReturn(arretePrefecturalGironde);

        // When
        val response = arreteRepository.save(arretePrefecturalGironde);

        // Then
        assertThat(response).isEqualTo(arretePrefecturalGironde);
        verify(arreteJpaRepository).save(arretePrefecturalGironde);

    }

    @Test
    void should_delete_arrete() {
        //Given
        doNothing().when(arreteJpaRepository).deleteById(anyLong());

        // When
        arreteRepository.deleteById(0L);

        // Then
        verify(arreteJpaRepository).deleteById(0L);

    }

}