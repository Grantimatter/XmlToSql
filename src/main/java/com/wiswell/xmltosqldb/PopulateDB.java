package com.wiswell.xmltosqldb;

import com.wiswell.xmltosqldb.model.KeyValue;
import com.wiswell.xmltosqldb.service.KeyValueService;
import com.wiswell.xmltosqldb.util.XmlHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PopulateDB {

    private final KeyValueService keyValueService;

    private static String FILEPATH = "C:\\Users\\gwisw\\Desktop\\testfile.txt";

    public PopulateDB(KeyValueService keyValueService) {
        this.keyValueService = keyValueService;
    }

    public void start() {
        List<KeyValue> keyValueList = XmlHelper.getKeyValuePairs(FILEPATH);

        //keyValueList.forEach(t -> System.out.println(t));

        System.out.println(String.format("Retrieved %d elements", keyValueList.size()));

        keyValueList.forEach(keyValueService::createKeyValuePair);
    }
}
