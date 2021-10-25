package com.wiswell.xmltosqldb.configuration;

import com.wiswell.xmltosqldb.model.Database;
import com.wiswell.xmltosqldb.util.DatabaseContextHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class AbstractDataSource {

    private final Logger log = LogManager.getLogger(AbstractDataSource.class);

    private final DatabaseContextHolder databaseContextHolder;

    public AbstractDataSource(DatabaseContextHolder databaseContextHolder) {
        this.databaseContextHolder = databaseContextHolder;
    }

    @Bean
    public DataSource getDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        List<Database> databaseList = DatabaseContextHolder.getDatabaseList();
        List<DataSource> dataSourceList = new ArrayList<>();

        for (int i = 0; i < databaseList.size(); i++) {
            DataSource newDataSource = DataSourceBuilder.create()
                    .url(databaseList.get(i).getUrl())
                    .username(databaseList.get(i).getUsername())
                    .password(databaseList.get(i).getPassword())
                    .build();

            dataSourceList.add(newDataSource);
            targetDataSources.put(databaseList.get(i), newDataSource);
        }

        DbRoutingDataSource dbRoutingDataSource = new DbRoutingDataSource();
        dbRoutingDataSource.setTargetDataSources(targetDataSources);
        dbRoutingDataSource.setDefaultTargetDataSource(dataSourceList.get(0));
        return dbRoutingDataSource;

        /*
        Database db = databaseContextHolder.getCurrentDatabase();
        log.info(String.format("Database: %s", db));

        return DataSourceBuilder.create()
                .url(db.getUrl())
                .username(db.getUsername())
                .password(db.getPassword())
                .driverClassName("org.postgresql.Driver")
                .build();
                */
    }

}
