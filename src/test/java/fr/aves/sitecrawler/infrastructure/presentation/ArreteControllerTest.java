package fr.aves.sitecrawler.infrastructure.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.aves.sitecrawler.domain.entity.Arrete;
import fr.aves.sitecrawler.domain.entity.ArreteSource;
import fr.aves.sitecrawler.domain.entity.Prefecture;
import fr.aves.sitecrawler.domain.services.ArreteService;
import io.vavr.control.Either;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArreteController.class)
class ArreteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private ArreteService arreteService;

    private final Arrete arrete = new Arrete(0L, new Prefecture(0L, "Some-prefecture", List.of(), ArreteSource.BROWSE, "", "", ""), "some-description", new Date(), "");

    @Test
    void should_return_204_if_there_was_no_arrete_found() throws Exception {
        // Given
        when(arreteService.getArrete(Mockito.anyLong())).thenReturn(Either.left(new NoSuchElementException()));

        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/arrete/0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        // Then
        verify(arreteService).getArrete(0L);
    }

    @Test
    void should_return_200_with_an_arrete() throws Exception {
        // Given
        when(arreteService.getArrete(Mockito.anyLong())).thenReturn(Either.right(arrete));

        // When
        val response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/arrete/0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        val responseObject = mapper.readValue(response.getResponse().getContentAsString(), Arrete.class);
        assertThat(responseObject).isEqualTo(arrete);
        verify(arreteService).getArrete(0L);
    }

    @Test
    void should_delete_an_arrete() throws Exception {
        // Given
        doNothing().when(arreteService).deleteArrete(Mockito.anyLong());

        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/arrete/0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Then
        verify(arreteService).deleteArrete(0L);

    }

}