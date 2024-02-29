package com.example.http;

import client.to.ResourceTO;
import com.example.config.IKeys;
import com.example.config.PropertiesHandler;
import com.example.http.uri.Requests;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class HttpHandler<T> {
    private final String defaultServerUrl;

    private final HttpClient httpClient;

    public HttpHandler() {
        System.out.println("HttpHandler constructor");
        defaultServerUrl = PropertiesHandler.getProperties().getProperty(IKeys.DEFAULT_SERVER_URL.getValue());
        httpClient = HttpClient.newHttpClient();
    }

    /**
     * если object null, то запрос будет GET
     *
     * @param request - один из наших созданных {@link Requests}
     * @param object  - объект, который мы отсылаем на сервер. Например, для сохранения
     * @return всегда получаем в ответ JSON в виде String
     */
    public T sendRequest(Requests request, String object) {
        HttpRequest.Builder uri = HttpRequest.newBuilder().uri(URI.create(defaultServerUrl.concat(request.getPath())));
        if (object == null) {
            uri.GET();
        } else {
            uri.POST(HttpRequest.BodyPublishers.ofString(object));
        }
        HttpRequest finalRequest = uri.build();
        try {
            String answer = httpClient.send(finalRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)).body();
            return parseResult(answer);

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException("http exception", e);
        }
    }

    private T parseResult(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, ResourceTO.class);
            return mapper.readValue(response, listType);
        } catch (IOException e) {
            throw new RuntimeException("exception by parsing json answer", e);
        }
    }
}
