package com.sb.solutions.api.memo.service;

import java.util.List;

import com.sb.solutions.api.memo.entity.MemoType;
import com.sb.solutions.core.enums.Status;
import com.sb.solutions.core.service.BaseService;

public interface MemoTypeService extends BaseService<MemoType> {

    List<MemoType> findByStatus(Status status);

    List<MemoType> saveAll(List<MemoType> types);

    void delete(MemoType type);
}
