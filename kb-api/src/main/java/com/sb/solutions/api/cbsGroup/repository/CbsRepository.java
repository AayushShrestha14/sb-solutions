package com.sb.solutions.api.cbsGroup.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sb.solutions.api.cbsGroup.config.DataSourceConfig;
import com.sb.solutions.api.cbsGroup.constants.CbsConstant;
import com.sb.solutions.core.exception.ServiceValidationException;

/**
 * @author : Rujan Maharjan on  12/20/2020
 * This repository is use to fetch data from remote connection
 **/
@Repository
@Slf4j
public class CbsRepository {

    private final DataSourceConfig dataSourceConfig;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CbsRepository(DataSourceConfig dataSourceConfig,
        NamedParameterJdbcTemplate jdbcTemplate) {
        this.dataSourceConfig = dataSourceConfig;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String,Object>> getAllData() {
        Map<String, String> map = new HashMap<>();
        jdbcTemplate.getJdbcTemplate().setDataSource(dataSourceConfig.dataSourceCsb());
        try {
            log.info("fetch data from remote server");
            return jdbcTemplate.queryForList(
                String.format(CbsConstant.QUERY_FOR_LIST, dataSourceConfig.getTableName()), map);
        } catch (Exception e) {
            log.error("unable to fetch data from remote server:::{}", e.getMessage());
            throw new ServiceValidationException("Unable to connect to remote server");
        }
    }
}
