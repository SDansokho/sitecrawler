package fr.aves.sitecrawler.infrastructure.presentation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.aves.sitecrawler.domain.entity.ArreteSource;
import fr.aves.sitecrawler.domain.entity.Prefecture;
import fr.aves.sitecrawler.domain.services.PrefectureService;
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

import java.util.List;
import java.util.NoSuchElementException;

import static fr.aves.sitecrawler.domain.entity.ArreteSource.SEARCH_ENGINE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrefectureController.class)
class PrefectureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private PrefectureService prefectureService;

    private final Prefecture prefectureGironde = new Prefecture(0L, "Gironde", List.of(), ArreteSource.BROWSE, "", "", "");
    private final Prefecture prefectureSeine = new Prefecture(0L, "Seine", List.of(), ArreteSource.BROWSE, "", "", "");

    @Test
    void should_return_a_prefecture() throws Exception {
        // Given
        when(prefectureService.getPrefecture(Mockito.anyLong())).thenReturn(Either.right(prefectureGironde));

        // When
        val response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/prefecture/0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        val responseObject = mapper.readValue(response.getResponse().getContentAsString(), Prefecture.class);
        assertThat(responseObject).isEqualTo(prefectureGironde);
        verify(prefectureService).getPrefecture(0L);
    }

    @Test
    void should_return_204() throws Exception {
        // Given
        when(prefectureService.getPrefecture(Mockito.anyLong())).thenReturn(Either.left(new NoSuchElementException()));

        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/prefecture/0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        // Then
        verify(prefectureService).getPrefecture(0L);
    }

    @Test
    void should_return_all_prefectures() throws Exception {
        // Given
        final var prefectures = List.of(prefectureGironde, prefectureSeine);
        when(prefectureService.getAllPrefectures()).thenReturn(prefectures);

        // When
        val result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/prefecture")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        val responseObject = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Prefecture>>() {
        });
        assertThat(responseObject).isEqualTo(prefectures);
        verify(prefectureService).getAllPrefectures();
    }

    @Test
    void should_create_and_return_a_prefecture() throws Exception {
        // Given
        when(prefectureService.createPrefecture(prefectureGironde)).thenReturn(prefectureGironde);

        // When
        val response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/prefecture")
                        .content(mapper.writeValueAsString(prefectureGironde))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        val responseObject = mapper.readValue(response.getResponse().getContentAsString(), Prefecture.class);
        assertThat(responseObject).isEqualTo(prefectureGironde);
        verify(prefectureService).createPrefecture(prefectureGironde);
    }

    @Test
    void should_update_and_return_a_prefecture() throws Exception {
        // Given
        when(prefectureService.updatePrefecture(0L, prefectureGironde)).thenReturn(prefectureGironde);

        // When
        val response = mockMvc.perform(MockMvcRequestBuilders
                        .put("/prefecture/0")
                        .content(mapper.writeValueAsString(prefectureGironde))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        val responseObject = mapper.readValue(response.getResponse().getContentAsString(), Prefecture.class);
        assertThat(responseObject).isEqualTo(prefectureGironde);
        verify(prefectureService).updatePrefecture(0L, prefectureGironde);
    }

    @Test
    void should_switch_source_and_return_a_prefecture() throws Exception {
        // Given
        doNothing().when(prefectureService).switchSource(0L, SEARCH_ENGINE);

        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/prefecture/0")
                        .content(mapper.writeValueAsString(SEARCH_ENGINE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        // Then
        verify(prefectureService).switchSource(0L, SEARCH_ENGINE);
    }

    @Test
    void should_delete_a_prefecture() throws Exception {
        // Given
        doNothing().when(prefectureService).deletePrefecture(0L);

        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/prefecture/0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        // Then
        verify(prefectureService).deletePrefecture(0L);
    }

    @Test
    void should_read_prefecture_site() throws Exception {
        // Given
        when(prefectureService.getPrefecture(Mockito.anyLong())).thenReturn(Either.right(prefectureGironde));
        doNothing().when(prefectureService).crawlSite(Mockito.anyLong());

        // When
        val response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/prefecture/0/content")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        // Then
        verify(prefectureService).crawlSite(0L);
    }
}