package com.sb.solutions.api.cbsGroup.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.sb.solutions.api.cbsGroup.entity.CbsGroup;
import com.sb.solutions.api.cbsGroup.repository.CbsGroupRepository;
import com.sb.solutions.core.constant.AppConstant;

/**
 * @author : Rujan Maharjan on  12/22/2020
 **/

@Service
@Slf4j
public class CbsGroupServiceImpl implements CbsGroupService {

    private final CbsGroupRepository cbsGroupRepository;
    private final GetCbsData getCbsData;
    private final static String OBLIGOR_KEY = "obligor";

    private ObjectMapper mapper = new ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .setDateFormat(new SimpleDateFormat(AppConstant.DATE_FORMAT));

    public CbsGroupServiceImpl(
        CbsGroupRepository cbsGroupRepository,
        GetCbsData getCbsData) {
        this.cbsGroupRepository = cbsGroupRepository;
        this.getCbsData = getCbsData;
    }

    @Override
    public List<CbsGroup> findAll() {
        return cbsGroupRepository.findAll();
    }

    @Override
    public CbsGroup findOne(Long id) {
        return cbsGroupRepository.findById(id).get();
    }

    @Override
    public CbsGroup save(CbsGroup cbsGroup) {
        return cbsGroupRepository.save(cbsGroup);
    }

    @Override
    public Page<CbsGroup> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Transactional
    @Override
    public List<CbsGroup> saveAll(List<CbsGroup> list) {
        cbsGroupRepository.deleteAllInBatch();
        List<Map<String, Object>> cbsRemoteData = getCbsData.getAllData();

        //if only to save data with obl nt null
        List<Map<String, Object>> cbsRemoteDataWithOblNotNull = cbsRemoteData.stream().filter(
            f -> f.containsKey(OBLIGOR_KEY) && !ObjectUtils.isEmpty(f.get(OBLIGOR_KEY))
        ).collect(Collectors.toList());
        List<CbsGroup> cbsGroupList = cbsRemoteData.stream().map(m -> {
            CbsGroup cbsGroup = new CbsGroup();
            cbsGroup.setObligor(ObjectUtils.isEmpty(m.get(OBLIGOR_KEY)) ? null
                : m.get(OBLIGOR_KEY).toString().trim());
            try {
                cbsGroup.setJsonData(mapper.writeValueAsString(m));
            } catch (JsonProcessingException e) {
                log.error("unable to parse json cbs data");
            }
            return cbsGroup;
        }).collect(Collectors.toList());

        return cbsGroupRepository.saveAll(cbsGroupList);
    }

    @Override
    public List<CbsGroup> findCbsGroupByObl(String obligor) {
        return cbsGroupRepository.findCbsGroupByObligor(obligor);
    }

    @Scheduled(cron = "0 0 20 * * ?")
    public void fetchData(){
        this.saveAll(new ArrayList<>());
    }
}
