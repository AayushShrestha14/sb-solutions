package com.sb.solutions.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * BaseRepository
 *
 * @param <T> Entity
 * @param <I> type of Entity ID
 *
 * example: MemoRepository
 */
public interface BaseRepository<T, I> extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {

}
