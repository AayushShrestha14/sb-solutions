package com.sb.solutions.api.userNotification.repository;

import com.sb.solutions.api.userNotification.entity.Message;
import com.sb.solutions.core.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("select COUNT(n) from Message n where n.status=?1 and n.toId=?2")
    long countCurrentUserNotification(Status status, long toId);
}
