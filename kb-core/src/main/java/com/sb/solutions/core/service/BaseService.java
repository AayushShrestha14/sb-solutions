package com.sb.solutions.core.service;

import java.util.List;

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


}
