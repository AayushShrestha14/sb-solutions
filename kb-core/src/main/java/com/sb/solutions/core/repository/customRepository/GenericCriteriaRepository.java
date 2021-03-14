package com.sb.solutions.core.repository.customRepository;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * @author : Rujan Maharjan on  3/11/2021
 * Not completed
 **/

@Component
public class GenericCriteriaRepository<T, D> {

    private EntityManager entityManager;

    public GenericCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page<D> getPageable(Map<String, String> s, Pageable pageable,
        Specification<T> spec, Class<T> rootType, Class<D> type) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<D> q = cb.createQuery(type);
        Root<T> root = q.from(rootType);
        List<D> resultList = entityManager
            .createQuery(q.where(spec.toPredicate(root, q, cb)))
            .setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize())
            .getResultList();
        return new PageImpl<>(resultList, pageable, 0);
    }
}
