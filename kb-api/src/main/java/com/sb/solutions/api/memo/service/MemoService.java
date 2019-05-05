package com.sb.solutions.api.memo.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sb.solutions.api.memo.entity.Memo;
import com.sb.solutions.core.service.BaseService;

public interface MemoService extends BaseService<Memo> {

    void delete(Memo memo);

    void deleteById(long id);

    Page<Memo> findPageable(Map<String, String> filterParams, Pageable pageable);
}
