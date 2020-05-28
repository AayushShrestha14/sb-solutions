package com.sb.solutions.api.preference.blacklist.repository;

import java.util.List;

import com.sb.solutions.api.preference.blacklist.entity.BlackList;
import com.sb.solutions.core.repository.BaseRepository;

public interface BlackListRepository extends BaseRepository<BlackList, Long> {
    List<BlackList> findBlackListByRef(String ref);
}
