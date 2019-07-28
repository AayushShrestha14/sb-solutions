package com.sb.solutions.web.statistics.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.dto.RestResponseDto;

@RestController
@RequestMapping(StatisticsController.API)
public class StatisticsController {

    public static final String API = "/v1/statistics";

    private final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    private final UserService userService;

    public StatisticsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/branch-vs-users")
    public final ResponseEntity<?> getStatisticsForBranchVsUsers() {
        logger.debug("REST request to get statistics for number of users per branch.");
        return new RestResponseDto().successModel(userService.getStatisticsForBranchVsUsers());
    }

    @GetMapping("/role-vs-users")
    public final ResponseEntity<?> getStatisticsForRoleVsUsers() {
        logger.debug("REST request to get statistics for number of users per role.");
        return new RestResponseDto().successModel(userService.getStatisticsForRolesVsUsers());
    }
}
