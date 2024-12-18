package org.devkirby.app.hanimman.repositories;

import java.util.List;

import org.devkirby.app.hanimman.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByIdInAndIsReadFalse(List<Integer> ids);

    @Query("SELECT m FROM Message m WHERE (m.sender.id = :senderId AND m.receiver.id = :receiverId) OR (m.sender.id = :receiverId AND m.receiver.id = :senderId) ORDER BY m.createdAt")
    List<Message> findMessagesBetweenUsers(@Param("senderId") Integer senderId,
            @Param("receiverId") Integer receiverId);

    @Modifying
    @Query("UPDATE Message m SET m.isRead = true WHERE m.id IN :ids")
    int markMessagesAsRead(@Param("ids") List<Integer> ids);

}
