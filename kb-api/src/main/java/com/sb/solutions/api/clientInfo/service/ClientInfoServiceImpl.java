package com.sb.solutions.api.clientInfo.service;

import com.sb.solutions.api.clientInfo.entity.ClientInfo;
import com.sb.solutions.api.clientInfo.enums.Securities;
import com.sb.solutions.api.clientInfo.repository.ClientInfoRepository;
import com.sb.solutions.core.dto.SearchDto;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientInfoServiceImpl implements ClientInfoService {
    @Autowired
    ClientInfoRepository clientInfoRepository;

    @Override
    public List<ClientInfo> findAll() {
        return clientInfoRepository.findAll();
    }

    @Override
    public ClientInfo findOne(Long id) {
        return clientInfoRepository.getOne(id);
    }

    @Override
    public ClientInfo save(ClientInfo clientInfo) {
        String mapSecurity = "";
        for (Securities securities : clientInfo.getSecurities()) {
            mapSecurity += securities + ",";
        }

        clientInfo.setSecurity(mapSecurity);
        return clientInfoRepository.save(clientInfo);
    }

    @Override
    public Page<ClientInfo> findAllPageable(Object object, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchDto s = objectMapper.convertValue(object, SearchDto.class);
        return clientInfoRepository.clientInfoFilter(s.getName() == null ? "" : s.getName(), pageable);

    }
}
