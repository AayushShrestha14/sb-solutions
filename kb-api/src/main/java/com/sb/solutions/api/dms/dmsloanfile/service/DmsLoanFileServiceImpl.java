package com.sb.solutions.api.dms.dmsloanfile.service;

import com.google.gson.Gson;
import com.sb.solutions.api.dms.dmsloanfile.entity.DmsLoanFile;
import com.sb.solutions.api.dms.dmsloanfile.repository.DmsLoanFileRepository;
import com.sb.solutions.api.dms.dmsloanfile.repository.specification.DmsSpecBuilder;
import com.sb.solutions.core.date.validation.DateValidation;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.LoanType;
import com.sb.solutions.core.enums.Securities;
import com.sb.solutions.core.exception.ApiException;
import lombok.AllArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

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
        DmsLoanFile d = dmsLoanFileRepository.getOne(id);
        List<Map<Object, Object>> mapList = new ArrayList<>();
        String tempPath = d.getDocumentPath();
        List tempList = gson.fromJson(tempPath, List.class);
        List<String> documentNames = new ArrayList<>();
        List<String> documentPaths = new ArrayList<>();
        int count = 0;
        for (Object list : tempList) {
            String toString = list.toString();
            String[] arrayOfString = toString.split(":");
            documentNames.add(arrayOfString[0]);
            documentPaths.add(arrayOfString[1]);
        }
        for (String documentPath : documentPaths) {
            Map<Object, Object> map = new LinkedHashMap<>();
            map.put(documentNames.get(count), documentPath);
            count++;
            mapList.add(map);
        }
        d.setDocumentPathMaps(mapList);
        return d;
    }

    @Override
    public DmsLoanFile save(DmsLoanFile dmsLoanFile) {
        if (dmsLoanFile.getId() == null) {
            dmsLoanFile.setCurrentStage(null);
            dmsLoanFile.setPreviousStageList(null);
            dmsLoanFile.setDocumentStatus(DocStatus.PENDING);
            dmsLoanFile.setLoanType(LoanType.NEW_LOAN);
        }
        if (dateValidation.checkDate(dmsLoanFile.getTenure())) {
            throw new ApiException("Invalid Date");
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
