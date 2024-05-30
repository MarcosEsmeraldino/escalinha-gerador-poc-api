package com.escalinha.escalinhageradorpocapi.controller;

import com.escalinha.escalinhageradorpocapi.dto.*;
import com.escalinha.escalinhageradorpocapi.exception.CombinacaoValidationException;
import com.escalinha.escalinhageradorpocapi.service.CombinacaoService;
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
public class CombinacaoControllerTest {

    private static final String URL = "/v1/combinacoes";
    private static final String URL_LIMITE = "/v1/combinacoes";
    private static final String FILE_REQUEST_ESCALA = "data/request-escala.json";
    private static final String FILE_REQUEST_ESCALA_INVALIDA = "data/request-escala-invalida.json";
    private static final String FILE_REQUEST_GRUPO_INVALIDO = "data/request-grupo-invalido.json";
    private static final String FILE_REQUEST_CANDIDATO_INVALIDO = "data/request-candidato-invalido.json";

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CombinacaoController controller;

    @MockBean
    private CombinacaoService service;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {URL, URL_LIMITE})
    public void retornaSucesso200AoCombinar(String url) throws Exception {
        when(service.combinar(any()))
                .thenReturn(List.of(CombinacaoResponse.builder().build()));

        var request = post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(readJsonFile(FILE_REQUEST_ESCALA));

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));

        verify(service)
                .combinar(any());
    }

    @ParameterizedTest
    @ValueSource(strings = {FILE_REQUEST_ESCALA_INVALIDA, FILE_REQUEST_GRUPO_INVALIDO, FILE_REQUEST_CANDIDATO_INVALIDO})
    public void retornaErro400AoCombinar(String file) throws Exception {
        var request = post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(readJsonFile(file));

        mvc.perform(request)
                .andExpect(status().isBadRequest());

        verify(service, never())
                .combinar(any());
    }

    @Test
    public void retornaErro422AoCombinarComErroNegocio() throws Exception {

        when(service.combinar(any()))
                .thenThrow(new CombinacaoValidationException());

        var request = post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(readJsonFile(FILE_REQUEST_ESCALA));

        mvc.perform(request)
                .andExpect(status().isUnprocessableEntity());

        verify(service)
                .combinar(any());
    }

    private String readJsonFile(String filePath) throws IOException {
        var classLoader = getClass().getClassLoader();
        var file = new File(classLoader.getResource(filePath).getFile());
        var jsonNode = MAPPER.readValue(file, ObjectNode.class);
        return jsonNode.toString();
    }
}