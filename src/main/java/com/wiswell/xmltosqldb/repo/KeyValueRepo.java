package com.wiswell.xmltosqldb.repo;

import com.wiswell.xmltosqldb.model.KeyValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository @Transactional
public interface KeyValueRepo extends JpaRepository<KeyValue, Integer> {

    //List<KeyValueRepo> findAllByProperty_key(String property_key);
    //List<KeyValueRepo> findAllByProperty_value(String property_value);
}
