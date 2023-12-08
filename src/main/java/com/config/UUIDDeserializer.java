package com.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.UUID;

public class UUIDDeserializer extends JsonDeserializer<UUID> {
    @Override
    public UUID deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String uuidAsString = p.getValueAsString();
        return UUID.fromString(uuidAsString);
    }
}
