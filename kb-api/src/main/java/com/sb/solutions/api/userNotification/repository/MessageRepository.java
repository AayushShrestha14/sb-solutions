package com.sb.solutions.api.userNotification.repository;

import com.sb.solutions.api.userNotification.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
