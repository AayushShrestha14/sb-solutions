package com.sb.solutions.api.chat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sb.solutions.api.chat.entity.ChatMessage;

/**
 * @author : Rujan Maharjan on  3/26/2020
 **/
@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {

    Page<ChatMessage> findChatMessageByFromUserIdAndToUserIdOrToUserIdAndFromUserIdOrderBySendDateDesc(
        String fId, String tId, String d, String i,
        Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ChatMessage c set c.seenFlag = true WHERE c.fromUserId =:fromId AND c.toUserId=:toId AND c.seenFlag = false")
    void updateSeen(@Param("fromId") String fromId, @Param("toId") String toId);

    @Query(value = "SELECT COUNT(c) FROM ChatMessage c Where c.seenFlag= false and c.toUserId=:currentUser")
    Integer getUnSeenMsg(@Param("currentUser") String currentUser);

    @Query(value = "SELECT COUNT(c) FROM ChatMessage c Where c.seenFlag= false and c.toUserId=:currentUser and c.fromUserId=:fromId")
    Integer getUnSeenMsgWithRespectToSender(@Param("currentUser") String currentUser,
        @Param("fromId") String fromId);
}
