package com.sb.solutions.api.preference.blacklist.service;

import com.sb.solutions.api.preference.blacklist.entity.BlackList;
import com.sb.solutions.core.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlackListService extends BaseService<BlackList> {
    void saveList(List<BlackList> blackList);

    Page<BlackList> findAllBlackList(Pageable pageable);

    void removeById(Long id);

    boolean checkBlackListByRef(String ref);
}
