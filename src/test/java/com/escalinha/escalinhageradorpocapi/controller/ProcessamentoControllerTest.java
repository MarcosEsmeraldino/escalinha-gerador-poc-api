package com.escalinha.escalinhageradorpocapi.controller;

import com.escalinha.escalinhageradorpocapi.dto.*;
import com.escalinha.escalinhageradorpocapi.service.ProcessamentoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProcessamentoControllerTest {

    private static final String URL = "/v1/processamentos";
    private static final String URL_LIMITE = "/v1/processamentos?limite=5";
    private static final String URL_LIMITE_MENOR_QUE_1 = "/v1/processamentos?limite=0";
    private static final String FILE_REQUEST = "src/test/resources/data/request-escala.json";
    private static final String FILE_REQUEST_SEM_ID = "src/test/resources/data/request-escala-sem-id.json";
    private static final String FILE_REQUEST_SEM_GRUPOS = "src/test/resources/data/request-escala-sem-grupos.json";
    private static final String FILE_REQUEST_SEM_CANDIDATOS = "src/test/resources/data/request-escala-sem-candidatos.json";
    private static final String FILE_REQUEST_GRUPO_SEM_ID = "src/test/resources/data/request-escala-com-grupo-sem-id.json";
    private static final String FILE_REQUEST_CANDIDATO_SEM_ID = "src/test/resources/data/request-escala-com-candidato-sem-id.json";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProcessamentoController controller;

    @MockBean
    private ProcessamentoService service;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {URL, URL_LIMITE})
    public void retornaSucesso200AoProcessarEscala(String url) throws Exception {
        when(service.processar(any(), any()))
                .thenReturn(List.of(new EscalaPreenchida(null, null, null)));

        var request = post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getRequest(FILE_REQUEST));

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));

        verify(service)
                .processar(any(), any());
    }

    @Test
    public void retornaErro400AoProcessarEscalaComLimiteMenorQue1() throws Exception {
        var request = post(URL_LIMITE_MENOR_QUE_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getRequest(FILE_REQUEST));

        mvc.perform(request)
                .andExpect(status().isBadRequest());

        verify(service, never())
                .processar(any(), any());
    }

    @ParameterizedTest
    @ValueSource(strings = {FILE_REQUEST_SEM_ID, FILE_REQUEST_SEM_GRUPOS, FILE_REQUEST_SEM_GRUPOS, FILE_REQUEST_SEM_CANDIDATOS,
            FILE_REQUEST_GRUPO_SEM_ID, FILE_REQUEST_CANDIDATO_SEM_ID})
    public void retornaErro400AoProcessarEscala(String url) throws Exception {
        var request = post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(url);

        mvc.perform(request)
                .andExpect(status().isBadRequest());

        verify(service, never())
                .processar(any(), any());
    }

    private String getRequest(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}