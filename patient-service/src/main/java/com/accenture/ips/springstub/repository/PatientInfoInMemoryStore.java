package com.accenture.ips.springstub.repository;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class PatientInfoInMemoryStore {
    private final ConcurrentHashMap<String, ObjectNode> dataStore = new ConcurrentHashMap<>();

    public ObjectNode get(String key) {
        return dataStore.get(key);
    }

    public void put(String key, ObjectNode value) {
        dataStore.put(key, value);
    }
}
