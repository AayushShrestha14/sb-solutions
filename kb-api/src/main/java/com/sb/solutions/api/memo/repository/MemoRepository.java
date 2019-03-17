package com.sb.solutions.api.memo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sb.solutions.api.memo.entity.Memo;
import com.sb.solutions.core.enums.Review;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {
}
