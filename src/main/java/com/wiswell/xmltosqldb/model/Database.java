package com.wiswell.xmltosqldb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Database {

    private final Logger log = LogManager.getLogger(Database.class);

    private String url;
    private String username;
    private String password;
    private String xmlFilepath;

    public boolean Validate(String databaseId) {

        boolean valid = true;

        if(url.length() == 0) {
            String message = String.format("%s does not have a valid url", databaseId);
            log.warn(message);
            valid = false;
        }

        if(username.length() == 0) {
            String message = String.format("%s does not have a valid username", databaseId);
            log.warn(message);
            valid = false;
        }

        if(password.length() == 0) {
            String message = String.format("%s does not have a valid password", databaseId);
            log.warn(message);
            valid = false;
        }

        if(xmlFilepath.length() == 0) {
            String message = String.format("%s does not have a valid xml file", databaseId);
            log.warn(message);
            valid = false;
        }

        return valid;
    }
}
