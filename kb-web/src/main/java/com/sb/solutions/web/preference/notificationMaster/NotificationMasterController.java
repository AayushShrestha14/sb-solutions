package com.sb.solutions.web.preference.notificationMaster;

import com.sb.solutions.api.preference.notificationMaster.entity.NotificationMaster;
import com.sb.solutions.api.preference.notificationMaster.service.NotificationMasterService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.enums.NotificationMasterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/notification-master")
public class NotificationMasterController {

    private final NotificationMasterService notificationMasterService;

    public NotificationMasterController(
            @Autowired NotificationMasterService notificationMasterService) {
        this.notificationMasterService = notificationMasterService;
    }

    @GetMapping(value = "/upload")
    public ResponseEntity<?> upload(@RequestParam(value = "numOfDays") Integer numberOfDays) {

        NotificationMaster obj = new NotificationMaster(
                (NotificationMasterType.INSURANCE_EXPRIY_NOTIFY).toString(), numberOfDays);

        return new RestResponseDto().successModel(notificationMasterService.save(obj));
    }

}
