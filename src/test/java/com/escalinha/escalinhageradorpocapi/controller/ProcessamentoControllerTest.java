package com.escalinha.escalinhageradorpocapi.controller;

import com.escalinha.escalinhageradorpocapi.dto.*;
import com.escalinha.escalinhageradorpocapi.service.ProcessamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.io.File;
import java.io.IOException;
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
    private static final String URL_LIMITE_INVALIDO = "/v1/processamentos?limite=0";
    private static final String FILE_REQUEST_ESCALA = "data/request-escala.json";
    private static final String FILE_REQUEST_ESCALA_INVALIDA = "data/request-escala-invalida.json";
    private static final String FILE_REQUEST_GRUPO_INVALIDO = "data/request-grupo-invalido.json";
    private static final String FILE_REQUEST_CANDIDATO_INVALIDO = "data/request-candidato-invalido.json";

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

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
                .thenReturn(List.of(new EscalaPreenchidaDTO(null, null, null)));

        var request = post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(readJsonFile(FILE_REQUEST_ESCALA));

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));

        verify(service)
                .processar(any(), any());
    }

    @Test
    public void retornaErro400AoProcessarEscalaComLimiteMenorQue1() throws Exception {
        var request = post(URL_LIMITE_INVALIDO)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(readJsonFile(FILE_REQUEST_ESCALA));

        mvc.perform(request)
                .andExpect(status().isBadRequest());

        verify(service, never())
                .processar(any(), any());
    }

    @ParameterizedTest
    @ValueSource(strings = {FILE_REQUEST_ESCALA_INVALIDA, FILE_REQUEST_GRUPO_INVALIDO, FILE_REQUEST_CANDIDATO_INVALIDO})
    public void retornaErro400AoProcessarEscala(String file) throws Exception {
        var request = post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(readJsonFile(file));

        mvc.perform(request)
                .andExpect(status().isBadRequest());

        verify(service, never())
                .processar(any(), any());
    }

    private String readJsonFile(String filePath) throws IOException {
        var classLoader = getClass().getClassLoader();
        var file = new File(classLoader.getResource(filePath).getFile());
        var jsonNode = MAPPER.readValue(file, ObjectNode.class);
        return jsonNode.toString();
    }
}