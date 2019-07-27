package com.sb.solutions.api.userNotification.repository;

import com.sb.solutions.api.userNotification.entity.Sender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SenderRepository extends JpaRepository<Sender, Long> {

}
