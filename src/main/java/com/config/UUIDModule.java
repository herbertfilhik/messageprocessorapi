package com.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import java.util.UUID;

public class UUIDModule extends SimpleModule {
    public UUIDModule() {
        addDeserializer(UUID.class, new UUIDDeserializer());
    }
}
