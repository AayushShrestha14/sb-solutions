package com.sb.solutions.core.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * @author Rujan Maharjan on 2/18/2019
 */

public class PaginationUtils {

    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final String SORT_BY = "sortBy";
    public static final String SORT_ORDER = "sortOrder";

    public static Pageable pageable(int page, int size) {
        return PageRequest.of(page - 1, size);
    }

    public static Map<String, String> excludePageableProperties(Map<String, String> requestParam) {
        final List<String> pageableProperties = Lists.newArrayList(PAGE, SIZE, SORT_BY, SORT_ORDER);

        return requestParam.keySet().stream()
            .filter(k -> !pageableProperties.contains(k))
            .collect(Collectors.toMap(k -> k, requestParam::get));
    }
}
