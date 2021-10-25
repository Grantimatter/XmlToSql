package com.wiswell.xmltosqldb.configuration;

import com.wiswell.xmltosqldb.util.DatabaseContextHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DbRoutingDataSource extends AbstractRoutingDataSource {

    private Logger log = LogManager.getLogger(DbRoutingDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        return DatabaseContextHolder.getDatabase();
    }
}
