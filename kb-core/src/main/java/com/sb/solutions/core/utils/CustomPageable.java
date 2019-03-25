package com.sb.solutions.core.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * @author Rujan Maharjan on 2/18/2019
 */
@Component
public class CustomPageable {

    public Pageable pageable(int page, int size) {
        Pageable p = PageRequest.of(page - 1, size);
        return p;

    }
}
