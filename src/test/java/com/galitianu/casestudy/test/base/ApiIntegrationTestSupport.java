package com.galitianu.casestudy.test.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public abstract class ApiIntegrationTestSupport {

    protected final MockMvc mvc;
    protected final ObjectMapper objectMapper;

    protected ApiIntegrationTestSupport(MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    protected MvcResult performGet(String urlTemplate, Object... uriVariables) {
        return mvc.perform(get(urlTemplate, uriVariables)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();
    }

    @SneakyThrows
    protected MvcResult performPost(String url, Object body) {
        return mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(body)))
            .andReturn();
    }

    @SneakyThrows
    protected MvcResult performPut(String urlTemplate, Object body, Object... uriVariables) {
        return mvc.perform(put(urlTemplate, uriVariables)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(body)))
            .andReturn();
    }

    @SneakyThrows
    protected MvcResult performDelete(String urlTemplate, Object... uriVariables) {
        return mvc.perform(delete(urlTemplate, uriVariables)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();
    }

    @SneakyThrows
    protected <T> T convert(MvcResult result, Class<T> clazz) {
        return objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), clazz);
    }

    @SneakyThrows
    protected <T> T convert(MvcResult result, TypeReference<T> typeReference) {
        return objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), typeReference);
    }
}
