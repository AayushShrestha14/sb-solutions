package com.sb.solutions.api.memo.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.sb.solutions.BaseTestConfig;
import com.sb.solutions.api.memo.entity.MemoType;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = BaseTestConfig.class)
public class MemoTypeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MemoTypeRepository repository;

    public MemoTypeRepositoryTest() {
    }

    @Before
    public void loadMemoTypes() {
        final MemoType demo = new MemoType();
        demo.setName("Demo Type");

        final MemoType demo2 = new MemoType();
        demo2.setName("Demo Type 2");

        entityManager.persistAndFlush(demo);
        entityManager.persistAndFlush(demo2);
    }

    @Test
    public void testFindAllShouldReturnTwoRecords(){
        List<MemoType> memoTypes = repository.findAll();

        assertThat(memoTypes, hasSize(2));
    }

    @Test
    public void testFindByIdShoudReturnSingleRecord() {
        MemoType memoType = repository.findById(1L).get();

        assertThat(memoType, notNullValue());
        assertThat(memoType.getName(), equalTo("Demo Type"));
    }
}
