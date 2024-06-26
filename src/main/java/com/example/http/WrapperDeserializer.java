package com.example.http;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;

public class WrapperDeserializer extends JsonDeserializer<Wrapper<?>> implements ContextualDeserializer {

    private JavaType type;

    @Override
    public Wrapper<?> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Wrapper<?> wrapper = new Wrapper<>();
        wrapper.setValue(ctxt.readValue(jp, type));
        return wrapper;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        this.type = property.getType().containedType(0);
        return this;
    }
}
