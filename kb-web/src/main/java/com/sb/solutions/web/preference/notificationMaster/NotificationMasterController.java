package com.sb.solutions.web.preference.notificationMaster;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.preference.notificationMaster.entity.NotificationMaster;
import com.sb.solutions.api.preference.notificationMaster.service.NotificationMasterService;
import com.sb.solutions.core.dto.RestResponseDto;

@RestController
@RequestMapping("/v1/notification-master")
public class NotificationMasterController {

    private final NotificationMasterService service;

    public NotificationMasterController(
        @Autowired NotificationMasterService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody NotificationMaster notificationMaster) {
        return new RestResponseDto().successModel(service.save(notificationMaster));
    }

    @PostMapping("/one")
    public ResponseEntity<?> getOneWithSearch(@RequestBody Object search) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = objectMapper.convertValue(search, Map.class);
        return new RestResponseDto().successModel(service.findOneBySpec(map));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return new RestResponseDto().successModel(service.findAll());
    }
}
