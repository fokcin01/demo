package com.example.http;

import com.example.config.IKeys;
import com.example.config.PropertiesHandler;
import com.example.http.uri.Requests;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class HttpHandler {
    private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);
    private final String defaultServerUrl;

    private final HttpClient httpClient;

    public HttpHandler() {
        logger.info("HttpHandler constructor");
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
    public String sendRequestAndGetJson(Requests request, Serializable object) {
        HttpRequest.Builder uri = HttpRequest.newBuilder().uri(URI.create(defaultServerUrl.concat(request.getPath())));
        if (object == null) {
            uri.GET();
        } else {
            try {
                String jsonValue = new ObjectMapper().writeValueAsString(object);
                logger.info("json value: " + jsonValue);
                uri.POST(HttpRequest.BodyPublishers.ofString(jsonValue));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        HttpRequest finalRequest = uri.build();
        try {
            return httpClient.send(finalRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)).body();
        } catch (InterruptedException | IOException e) {
            logger.error("exception in sending http request", e);
            return null;
        }
    }

//    private T parseResult(String response, Class<? extends Serializable> desClass) {
//        if (desClass == null || response == null || response.isEmpty()) {
//            return null;
//        }
//        if (desClass.equals(String.class)) {
//            return (T) response;
//        }
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            TypeReference<T> typeReference = new TypeReference<>() {};
//            logger.info("tr: " + typeReference.getType());
//            logger.info(T instanceof LoginAnswer);
//            Object o = mapper.readValue(response, typeReference);
//            logger.info("object::: " + (T)o);
//            return (T)o;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, desClass);
//            return mapper.readValue(response, listType);
//        } catch (IOException e) {
//            throw new RuntimeException("exception by parsing json answer", e);
//        }
//    }
}
