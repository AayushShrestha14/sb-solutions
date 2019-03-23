package com.sb.solutions.api.memo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sb.solutions.api.memo.entity.Memo;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {
}
