package com.sb.solutions.core.repository.customCriteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.stereotype.Component;

import com.sb.solutions.core.repository.customCriteria.dto.CriteriaDto;
import com.sb.solutions.core.repository.customCriteria.dto.InitQuery;

/**
 * @author : Rujan Maharjan on  5/9/2021
 **/
@Component
public class BaseCriteriaQuery<E, D> {


    public List<D> getList(CriteriaDto<E, D> criteriaDto, EntityManager entityManager) {
        InitQuery<E, D> initQuery = initializeQuery(criteriaDto, entityManager);

        for (String joinCol : criteriaDto.getJoinColumn()) {
            initQuery.getRoot().join(joinCol, JoinType.LEFT);
        }

        initQuery.getCriteriaQuery().select(
            initQuery.getCriteriaBuilder().construct(
                criteriaDto.getDtoClass(),
                getSelection(criteriaDto.getColumns(), initQuery.getRoot())
            ));

        TypedQuery<D> typedQuery = entityManager
            .createQuery(
                initQuery.getCriteriaQuery().where(criteriaDto.getSpecification()
                    .toPredicate(initQuery.getRoot(), initQuery.getCriteriaQuery(),
                        initQuery.getCriteriaBuilder())));
        return typedQuery.getResultList();
    }


    private InitQuery<E, D> initializeQuery(CriteriaDto<E, D> criteriaDto,
        EntityManager entityManager) {
        InitQuery<E, D> initQuery = new InitQuery<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<D> q = criteriaBuilder.createQuery(criteriaDto.getDtoClass());
        Root<E> root = q.from(criteriaDto.getRootClass());
        initQuery.setCriteriaBuilder(criteriaBuilder);
        initQuery.setRoot(root);
        initQuery.setCriteriaQuery(q);
        return initQuery;
    }

    private List<String> columnSplitter(String inner) {
        return Arrays.asList(inner.split("\\."));
    }


    private Selection<?>[] getSelection(String[] columnList, Root<E> root) {
        List<Selection<?>> selections = new ArrayList();
        for (String column : columnList) {
            if (!column.contains(".")) {
                selections.add(root.get(column));
            } else {
                List<String> stringList = columnSplitter(column);

                if (stringList.size() == 2) {
                    selections.add(root.get(stringList.get(0)).get(stringList.get(1)));
                }
                if (stringList.size() == 3) {
                    selections.add(
                        root.get(stringList.get(0)).get(stringList.get(1)).get(stringList.get(2)));
                }

                if (stringList.size() == 4) {
                    selections.add(
                        root.get(stringList.get(0)).get(stringList.get(1)).get(stringList.get(2))
                            .get(stringList.get(3)));
                }

                if (stringList.size() == 5) {
                    selections.add(
                        root.get(stringList.get(0)).get(stringList.get(1)).get(stringList.get(2))
                            .get(stringList.get(3)).get(stringList.get(4)));
                }
            }

        }
        return selections.toArray(new Selection[selections.size()]);
    }


}
