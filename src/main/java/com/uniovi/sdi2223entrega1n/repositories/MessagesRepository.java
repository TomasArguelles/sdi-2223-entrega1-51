package com.uniovi.sdi2223entrega1n.repositories;

import com.uniovi.sdi2223entrega1n.entities.Message;
import com.uniovi.sdi2223entrega1n.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MessagesRepository extends CrudRepository<Message, Long> {
    @Modifying
    @Transactional
    @Query("delete from Message where id=?1")
    void removeBySender(Long id);
}
