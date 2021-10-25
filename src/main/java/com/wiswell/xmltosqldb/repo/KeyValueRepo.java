package com.wiswell.xmltosqldb.repo;

import com.wiswell.xmltosqldb.model.KeyValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository @Transactional
public interface KeyValueRepo extends JpaRepository<KeyValue, Integer> {

}
