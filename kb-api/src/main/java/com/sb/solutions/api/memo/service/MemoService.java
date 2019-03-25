package com.sb.solutions.api.memo.service;

import com.sb.solutions.api.memo.entity.Memo;
import com.sb.solutions.core.service.BaseService;

public interface MemoService extends BaseService<Memo> {
    void delete(Memo memo);
}
