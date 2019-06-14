package com.sb.solutions.api.dms.dmsloanfile.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.dms.dmsloanfile.entity.DmsLoanFile;
import com.sb.solutions.api.dms.dmsloanfile.repository.DmsLoanFileRepository;
import com.sb.solutions.api.dms.dmsloanfile.repository.specification.DmsSpecBuilder;
import com.sb.solutions.core.date.validation.DateValidation;
import com.sb.solutions.core.enums.Securities;
import com.sb.solutions.core.exception.ServiceValidationException;
import com.sb.solutions.core.exception.handler.Violation;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DmsLoanFileServiceImpl implements DmsLoanFileService {

    DmsLoanFileRepository dmsLoanFileRepository;
    private Gson gson;
    private DateValidation dateValidation;

    @Override
    public List<DmsLoanFile> findAll() {
        return dmsLoanFileRepository.findAll();
    }

    @Override
    public DmsLoanFile findOne(Long id) {
        final DmsLoanFile d = dmsLoanFileRepository.getOne(id);
        return d;
    }

    @Override
    public DmsLoanFile save(DmsLoanFile dmsLoanFile) {

        if (dateValidation.checkDate(dmsLoanFile.getTenure())) {
            final Violation violation = new Violation("tenure", dmsLoanFile.getTenure(),
                "Invalid tenure date");

            throw new ServiceValidationException("Invalid dms loan", Lists.newArrayList(violation));
        }

        dmsLoanFile.setDocumentPath(gson.toJson(dmsLoanFile.getDocumentMap()));
        dmsLoanFile.setCreatedAt(new Date());
        String mapSecurity = "";
        for (Securities securities : dmsLoanFile.getSecurities()) {
            mapSecurity += securities.ordinal() + ",";
            System.out.println(mapSecurity);
        }
        mapSecurity = mapSecurity.substring(0, mapSecurity.length() - 1);
        dmsLoanFile.setSecurity(mapSecurity);
        return dmsLoanFileRepository.save(dmsLoanFile);
    }

    @Override
    public Page<DmsLoanFile> findAllPageable(Object object, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> s = objectMapper.convertValue(object, Map.class);
        final DmsSpecBuilder dmsSpecBuilder = new DmsSpecBuilder(s);
        final Specification<DmsLoanFile> specification = dmsSpecBuilder.build();
        return dmsLoanFileRepository.findAll(specification, pageable);
    }
}
