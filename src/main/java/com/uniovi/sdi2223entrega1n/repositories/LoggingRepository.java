package com.uniovi.sdi2223entrega1n.repositories;

import com.uniovi.sdi2223entrega1n.entities.CustomLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoggingRepository extends CrudRepository<CustomLog, Long> {

    @Query("SELECT l FROM CustomLog l WHERE l.logType = ?1 ORDER BY l.createdAt DESC")
    List<CustomLog> findAllByLogType(final String logType);

    @Query("SELECT l FROM CustomLog l ORDER BY l.createdAt DESC")
    List<CustomLog> findAllOrderByDate();
}
