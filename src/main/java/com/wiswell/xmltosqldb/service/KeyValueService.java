package com.wiswell.xmltosqldb.service;

import com.wiswell.xmltosqldb.model.KeyValue;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import com.wiswell.xmltosqldb.repo.KeyValueRepo;

@Service @EntityScan("com.wiswell.xmltosqldb.*")
@ComponentScan(basePackages = {"com.wiswell.xmltosqldb.*"})
@EnableJpaRepositories("com.wiswell.xmltosqldb.*")
public class KeyValueService {

    private final KeyValueRepo keyValueRepo;

    public KeyValueService(KeyValueRepo keyValueRepo) {
        this.keyValueRepo = keyValueRepo;
    }

    public void createKeyValuePair(KeyValue keyValue) {
        try {
            keyValueRepo.save(keyValue);
        } catch (Exception e) {
            System.out.println(String.format("Could not add %s to the database", keyValue));
        }
    }

}
