package com.sb.solutions.api.dms.dmsloanfile.service;

import com.google.gson.Gson;
import com.sb.solutions.api.dms.dmsloanfile.entity.DmsLoanFile;
import com.sb.solutions.api.dms.dmsloanfile.repository.DmsLoanFileRepository;
import com.sb.solutions.api.loanDocument.entity.LoanDocument;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.enums.Securities;
import lombok.AllArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class DmsLoanFileServiceImpl implements DmsLoanFileService {
    DmsLoanFileRepository dmsLoanFileRepository;
    private Gson gson;

    @Override
    public List<DmsLoanFile> findAll() {
        return dmsLoanFileRepository.findAll();
    }

    @Override
    public DmsLoanFile findOne(Long id) {
        DmsLoanFile d = dmsLoanFileRepository.getOne(id);
        List<Map<Object, Object>> mapList = new ArrayList<>();
        String tempPath = d.getDocumentPath();
        List tempList = gson.fromJson(tempPath,List.class);
        Set<LoanDocument> documents= d.getDocuments();
        System.out.println(documents);
        List<String> documentName = new ArrayList<>();
        int count = 0;
        for(LoanDocument loanDocument: documents){
            documentName.add(loanDocument.getName());
        }
        System.out.println(documentName);
        System.out.println(tempList);

        for(Object list:tempList){
            Map<Object, Object> map = new LinkedHashMap<>();
            map.put(documentName.get(count),list);
            mapList.add(map);
            count++;
        }
        System.out.println(mapList);
        d.setDocumentPathDocument(mapList);
        return d;
    }

    @Override
    public DmsLoanFile save(DmsLoanFile dmsLoanFile) {
        System.out.println(dmsLoanFile);
        System.out.println(dmsLoanFile.getDocumentMap());
      dmsLoanFile.setDocumentPath(gson.toJson(dmsLoanFile.getDocumentPaths()));
        dmsLoanFile.setCreatedAt(new Date());
        String mapSecurity = "";
        for (Securities securities : dmsLoanFile.getSecurities()) {
            mapSecurity += securities.ordinal() + ",";
        }
        mapSecurity = mapSecurity.substring(0, mapSecurity.length() - 1);
        System.out.println(mapSecurity);
        dmsLoanFile.setSecurity(mapSecurity);
        return dmsLoanFileRepository.save(dmsLoanFile);
    }

    @Override
    public Page<DmsLoanFile> findAllPageable(Object object, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchDto s = objectMapper.convertValue(object, SearchDto.class);
        return dmsLoanFileRepository.DmsLoanFileFilter(s.getName() == null ? "" : s.getName(), pageable);

    }
}
