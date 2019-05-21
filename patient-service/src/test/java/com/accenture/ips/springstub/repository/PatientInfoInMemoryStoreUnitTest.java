package com.accenture.ips.springstub.repository;

import com.accenture.ips.springstub.util.JsonHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.Assert.*;

import org.junit.Test;

public class PatientInfoInMemoryStoreUnitTest {

    private static final ObjectMapper OBJECT_MAPPER = JsonHelper.commonObjectMapper();

    @Test
    public void get_shouldReturnJson_whenExists() {
        PatientInfoInMemoryStore store = new PatientInfoInMemoryStore();

        store.put("abc123", OBJECT_MAPPER.createObjectNode());

        assertNotNull(store.get("abc123"));
        assertNull(store.get("does_not_exist"));
    }

    @Test
    public void put_shouldSave() {
        PatientInfoInMemoryStore store = new PatientInfoInMemoryStore();

        assertNull(store.get("123"));

        store.put("123", OBJECT_MAPPER.createObjectNode());

        assertNotNull(store.get("123"));
    }
}
