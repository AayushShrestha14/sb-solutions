package com.sb.solutions.core.repository.customCriteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
        for (String joinCol : criteriaDto.getJoinColumn()) {
            initQuery.getRoot().join(joinCol, JoinType.LEFT);
        }

        initQuery.getCriteriaQuery().select(
            initQuery.getCriteriaBuilder().construct(
                criteriaDto.getDtoClass(),
                getSelection(criteriaDto.getColumns(), initQuery.getRoot(),
                    initQuery.getCriteriaBuilder())
            ));
        return initQuery;
    }

    private List<String> columnSplitter(String inner) {
        return Arrays.asList(inner.split("\\."));
    }


    private Selection<?>[] getSelection(String[] columnList, Root<E> root, CriteriaBuilder c) {
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


    private Expression<String> concat(CriteriaBuilder criteriaBuilder, String delimiter,
        Expression<String>... expressions) {
        Expression<String> result = null;
        for (int i = 0; i < expressions.length; i++) {
            final boolean first = i == 0, last = i == (expressions.length - 1);
            final Expression<String> expression = expressions[i];
            if (first && last) {
                result = expression;
            } else if (first) {
                result = criteriaBuilder.concat(expression, delimiter);
            } else {
                result = criteriaBuilder.concat(result, expression);
                if (!last) {
                    result = criteriaBuilder.concat(result, delimiter);
                }
            }
        }
        return result;
    }

    public Page<D> getListPage(CriteriaDto<E, D> criteriaDto, EntityManager entityManager,
        Pageable pageable) {
        InitQuery<E, D> initQuery = initializeQuery(criteriaDto, entityManager);

        TypedQuery<D> typedQuery = entityManager
            .createQuery(
                initQuery.getCriteriaQuery().where(criteriaDto.getSpecification()
                    .toPredicate(initQuery.getRoot(), initQuery.getCriteriaQuery(),
                        initQuery.getCriteriaBuilder())).distinct(true)
            ).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize());

        List<D> resultList = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = initQuery.getCriteriaBuilder().createQuery(Long.class);
        Root<E> countEntity = countQuery.from(criteriaDto.getRootClass());
        countQuery.select(
            initQuery.getCriteriaBuilder().countDistinct(countEntity.get("id")));
        Long count = entityManager.createQuery(
            countQuery.where(criteriaDto.getSpecification()
                .toPredicate(countEntity, countQuery, initQuery.getCriteriaBuilder())))
            .getSingleResult();
        return new PageImpl<>(resultList, pageable, count);
    }

}
