package com.sb.solutions.core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Sunil Babu Shrestha on 12/31/2018
 */
public interface BaseService<T> {

    /**
     * @return
     */
    List<T> findAll();

    /**
     * @param id
     * @return
     */
    T findOne(Long id);

    /**
     * @param t
     * @return
     */
    T save(T t);

    /**
     * @param t
     * @return
     */
    Page<T> findAllPageable(T t, Pageable pageable);


}
