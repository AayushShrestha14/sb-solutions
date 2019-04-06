package com.sb.solutions.api.memo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.memo.entity.Memo;
import com.sb.solutions.api.memo.entity.MemoStage;
import com.sb.solutions.api.memo.enums.Stage;
import com.sb.solutions.api.memo.repository.MemoRepository;
import com.sb.solutions.core.enums.Status;

@Service
public class MemoServiceImpl implements MemoService {

    private final MemoRepository repository;

    public MemoServiceImpl(@Autowired MemoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Memo> findAll() {
        return repository.findAll();
    }

    @Override
    public Memo findOne(Long id) {
        return repository.getOne(id);
    }

    @Override
    public Memo save(Memo memo) {
        if (memo.getStage() == Stage.DRAFT) {
            final MemoStage memoStage = new MemoStage();
            memoStage.setSentBy(memo.getSentBy());
            memoStage.setSentTo(memo.getSentTo());
            memoStage.setStage(Stage.DRAFT);
            memoStage.setNote("Saved as Draft");

            memo.addMemoStage(memoStage);
        }

        return repository.save(memo);
    }

    @Override
    public Page<Memo> findAllPageable(Memo memo, Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void delete(Memo memo) {
        memo.setStatus(Status.DELETED);
        repository.save(memo);
    }

    @Override
    public void deleteById(long id) {
        final Memo memo = repository.getOne(id);
        memo.setStatus(Status.DELETED);

        repository.save(memo);
    }
}
