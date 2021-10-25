package com.wiswell.xmltosqldb.service;

import com.wiswell.xmltosqldb.model.KeyValue;
import com.wiswell.xmltosqldb.util.DatabaseContextHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.wiswell.xmltosqldb.repo.KeyValueRepo;

@Service
public class KeyValueService {

    private final Logger log = LogManager.getLogger(KeyValueService.class);

    private final KeyValueRepo keyValueRepo;

    public KeyValueService(KeyValueRepo keyValueRepo) {
        this.keyValueRepo = keyValueRepo;
    }

    public void createKeyValuePair(KeyValue keyValue) {
        try {
            keyValueRepo.save(keyValue);
        } catch (Exception e) {
            String message = String.format("Could not add %s to %s", keyValue, DatabaseContextHolder.getDatabase());
            log.warn(message);
        }
    }

}
