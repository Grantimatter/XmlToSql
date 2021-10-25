package com.wiswell.xmltosqldb;

import com.wiswell.xmltosqldb.configuration.DbRoutingDataSource;
import com.wiswell.xmltosqldb.model.KeyValue;
import com.wiswell.xmltosqldb.service.KeyValueService;
import com.wiswell.xmltosqldb.util.DatabaseContextHolder;
import com.wiswell.xmltosqldb.util.XmlHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PopulateDB {

    private final Logger log = LogManager.getLogger(PopulateDB.class);
    private final KeyValueService keyValueService;

    public PopulateDB(KeyValueService keyValueService) {
        this.keyValueService = keyValueService;
    }

    public void start() {

        for (int i = 0; i < DatabaseContextHolder.getDatabaseCount(); i++) {
            List<KeyValue> keyValueList = XmlHelper.getKeyValuePairs(DatabaseContextHolder.getDatabase().getXmlFilepath());

            String message = String.format("Retrieved %d elements", keyValueList.size());
            log.info(message);

            keyValueList.forEach(keyValueService::createKeyValuePair);

            DatabaseContextHolder.incrementDatabase();
        }
    }


}
