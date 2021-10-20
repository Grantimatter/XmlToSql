package com.wiswell.xmltosqldb.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class KeyValue {

    @Id
    private String property_key;
    private String property_value;

}
